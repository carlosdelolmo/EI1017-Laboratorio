import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Kmeans implements Algorithm<TableWithLabels,Row,String>{
    public Kmeans(int numberClusters, int iterations, long seed){
        this.numberClusters=numberClusters;
        this.iterations=iterations;
        this.seed=seed;
    }
    private int numberClusters;
    private int iterations;
    private long seed;
    private List<List<Double>> representantes;
    private List<String> etiquetas;
    private List<Integer> asignaciones;
    private TableWithLabels t;

    public void train(TableWithLabels t){
        this.t = t;
        representantes=obtenerRepresentantes(t);
        asignaciones= new ArrayList<Integer>(t.getNumFilas());
        for(int i=0; i<iterations;i++){
            // System.out.println("i: " + i);
            asignaciones=asignarAGrupos(t,asignaciones,representantes);
            calcularCentroides(t,asignaciones);
        }
    }

    private List<List<Double>> obtenerRepresentantes (TableWithLabels datos){
        List<List<Double>> representantes = new LinkedList<List<Double>>();
        Random random= new Random(seed);
        for(int i=0;i<numberClusters;i++) {
            int next = Math.abs(random.nextInt());
            int indice = (next)%datos.getNumFilas();
            // System.out.println(indice);
            List<Double> nuevafila = datos.getRowAt(indice).getData();
            representantes.add(nuevafila);
            // System.out.println(representantes);
        }
        return  representantes;
    }


    private List<Integer> asignarAGrupos (Table datos, List<Integer> listaGrupos, List<List<Double>> repre){
        for(int i=0; i<datos.getNumFilas();i++){
            int indice_grupo = calcularGrupo(datos.getRowAt(i), listaGrupos, repre);
            // System.out.println(listaGrupos);
            // System.out.println(i + " " + indice_grupo);
            listaGrupos.add(i,indice_grupo);
        }
        return listaGrupos;
    }

    private int calcularGrupo(Row fila, List<Integer> listaGrupos, List<List<Double>> repre){
        double min_dist=-1.0;
        int indice_grupo=0;
        for(int j=0;j< repre.size();j++){
            List<Double> actual = repre.get(j);
            if(actual.size() > 0) {
                double dist = metricaEuclidea(fila.getData(), repre.get(j));
                if (min_dist == -1.0 || min_dist > dist) {
                    min_dist = dist;
                    indice_grupo = j;
                }
            }
        }
        return indice_grupo;
    }

    private Double metricaEuclidea(List<Double> nuevaMuestra, List<Double> elementoFichero){
        Double sumatorio = 0.0;
        for(int i = 0; i < nuevaMuestra.size(); i++){

            // System.out.println(elementoFichero);
            // System.out.println(representantes);
            // System.out.println(nuevaMuestra.size() + " " + elementoFichero.size() + " " + i);
            // System.out.println(nuevaMuestra.get(i) +" " + elementoFichero.get(i));
            // System.out.println();

            Double resta = (nuevaMuestra.get(i) - elementoFichero.get(i));
            resta = resta * resta;
            sumatorio += resta;
        }
        return (Double) Math.sqrt(sumatorio);
    }

    private void calcularCentroides(TableWithLabels datos, List<Integer> asignaciones){
        List<Integer> puntosporgrupo= new ArrayList<Integer>(representantes.size());
        List<List<Double>> sumapuntos= new ArrayList<List<Double>>(representantes.size());
        for(int i = 0; i < representantes.size(); i++){
            puntosporgrupo.add(0);
            List<Double> auxiliar = new ArrayList<>(representantes.size());
            for(int j = 0; j < datos.getNumColumnas() - 1; j++)
                auxiliar.add(0.);
            sumapuntos.add(auxiliar);
        }
        for(int i=0; i<datos.getNumFilas();i++){
            int grupoActual=asignaciones.get(i);
            // System.out.println(puntosporgrupo + " " + puntosporgrupo.size());
            int puntosActuales = 1 + puntosporgrupo.get(grupoActual);
            // System.out.println(grupoActual + " " + puntosActuales);
            puntosporgrupo.set(grupoActual, puntosActuales);
            // System.out.println(sumapuntos.get(grupoActual) + " ~ " + datos.getRowAt(i).getData());
            sumapuntos.set(grupoActual,suma(sumapuntos.get(grupoActual),datos.getRowAt(i).getData()));
        }
        for(int i=0; i<numberClusters; i++){
            // System.out.println(puntosporgrupo);
            // System.out.println(representantes);
            // System.out.println(sumapuntos);
            // System.out.println(i);
            // System.out.println("R: " + multiplicar(sumapuntos.get(i), 1/puntosporgrupo.get(i)));
            // System.out.println("Res: "+ (float)  1/puntosporgrupo.get(i));
            // System.out.println();
            representantes.set(i, multiplicar(sumapuntos.get(i), (float) 1/puntosporgrupo.get(i)));
            calcularEtiqueta(datos, asignaciones, i);
        }
    }

    private List<Double> suma(List<Double> lista1, List<Double> lista2){
        List<Double> resultado = new ArrayList<Double>(lista1.size());
        for(int i=0; i<lista1.size(); i++){
            // System.out.println(i + " " + lista1.size() + " " + lista2.size());
            resultado.add(lista1.get(i)+lista2.get(i));
        }
        return resultado;
    }

    private List<Double> multiplicar(List<Double> lista, double constante){
        List<Double> resultado = new ArrayList<Double>(lista.size());
        for(int i=0; i<lista.size(); i++){
            resultado.add(lista.get(i)*constante);
        }
        return resultado;
    }

    public String estimate(Row d){
        int grupo = calcularGrupo(d, new LinkedList<>(), representantes);
        return calcularEtiqueta(t, asignaciones, grupo);
    }

    private String calcularEtiqueta(TableWithLabels t, List<Integer> asignaciones, int indiceGrupo){
        for(int i=0; i < t.getNumFilas(); i++){
            if(asignaciones.get(i) == indiceGrupo){
                return t.getRowAt(i).getLabel();
            }
        }
        return null;
    }


}
