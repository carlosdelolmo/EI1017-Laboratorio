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
            asignaciones=asignarAGrupos(t,asignaciones,representantes);
            calcularCentroides(t,asignaciones);
        }
    }

    private List<List<Double>> obtenerRepresentantes (TableWithLabels datos){
        List<List<Double>> representantes = new LinkedList<List<Double>>();
        Random random= new Random(seed);
        for(int i=0;i<numberClusters;i++) {
            int indice = random.nextInt();
            List<Double> nuevafila = datos.getRowAt(indice).getData();
            representantes.add(nuevafila);
        }
        return  representantes;
    }


    private List<Integer> asignarAGrupos (Table datos, List<Integer> listaGrupos, List<List<Double>> repre){
        for(int i=0; i<datos.getNumFilas();i++){
            int indice_grupo = calcularGrupo(datos.getRowAt(i), listaGrupos, repre);
            listaGrupos.set(i,indice_grupo);
        }
        return listaGrupos;
    }

    private int calcularGrupo(Row fila, List<Integer> listaGrupos, List<List<Double>> repre){
        double min_dist=-1.0;
        int indice_grupo=0;
        for(int j=0;j< repre.size();j++){
            double dist= metricaEuclidea(fila.getData(),repre.get(j));
            if(min_dist==-1.0 || min_dist>dist){
                min_dist=dist;
                indice_grupo=j;
            }
        }
        return indice_grupo;
    }

    private Double metricaEuclidea(List<Double> nuevaMuestra, List<Double> elementoFichero){
        Double sumatorio = 0.0;
        for(int i = 0; i < nuevaMuestra.size(); i++){
            Double resta = (nuevaMuestra.get(i) - elementoFichero.get(i));
            resta = resta * resta;
            sumatorio += resta;
        }
        return (Double) Math.sqrt(sumatorio);
    }

    private void calcularCentroides(TableWithLabels datos, List<Integer> asignaciones){
        List<Integer> puntosporgrupo= new ArrayList<Integer>(representantes.size());
        List<List<Double>> sumapuntos= new ArrayList<List<Double>>(representantes.size());
        for(int i=0; i<datos.getNumFilas();i++){
            int grupoActual=asignaciones.get(i);
            puntosporgrupo.set(grupoActual,1+puntosporgrupo.get(grupoActual));
            if(sumapuntos.get(grupoActual) == null) sumapuntos.set(grupoActual, new LinkedList<Double>());
            sumapuntos.set(grupoActual,suma(sumapuntos.get(grupoActual),datos.getRowAt(i).getData()));
        }
        for(int i=0; i<numberClusters; i++){
            representantes.set(i, multiplicar(sumapuntos.get(i), 1/puntosporgrupo.get(i)));
            calcularEtiqueta(datos, asignaciones, i);
        }

    }

    private List<Double> suma(List<Double> lista1, List<Double> lista2){
        List<Double> resultado = new ArrayList<Double>(lista1.size());
        for(int i=0; i<lista1.size(); i++){
            resultado.set(i, lista1.get(i)+lista2.get(i));
        }
        return resultado;
    }

    private List<Double> multiplicar(List<Double> lista, double constante){
        List<Double> resultado = new ArrayList<Double>(lista.size());
        for(int i=0; i<lista.size(); i++){
            resultado.set(i, lista.get(i)*constante);
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
