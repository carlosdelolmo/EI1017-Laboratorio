import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Kmeans implements Algorithm < TableWithLabels, Row, String > {
    public Kmeans(int numberClusters, int iterations, long seed) {
        this.numberClusters = numberClusters;
        this.iterations = iterations;
        this.seed = seed;
    }
    private int numberClusters;
    private int iterations;
    private long seed;
    private List < List < Double >> representantes;
    private List < String > etiquetas;
    private List < Integer > asignaciones;
    private TableWithLabels t;

    public void train(TableWithLabels tabla) {
        t = tabla;
        representantes = obtenerRepresentantes();
        asignaciones = new ArrayList < Integer > (t.getNumFilas());
        for (int i = 0; i < iterations; i++) {
            asignaciones = asignarAGrupos();
            calcularCentroides();
        }
    }

    public String estimate(Row d) {
        int indiceGrupo = calcularGrupo(d); // asigna a la variable indiceGrupo el numero de grupo del centroide más cercano a d
        return obtenerEtiqueta(indiceGrupo);
    }

    private List < List < Double >> obtenerRepresentantes() {   // Obtiene aleatoriamente representantes de grupos
        List < List < Double >> representantes = new LinkedList < List < Double >> ();
        Random random = new Random(seed);
        for (int i = 0; i < numberClusters; i++) {
            int next = Math.abs(random.nextInt());
            int indice = (next) % t.getNumFilas();
            List < Double > nuevafila = t.getRowAt(indice).getData();
            representantes.add(nuevafila);
        }
        return representantes;
    }

    private List < Integer > asignarAGrupos() { // Asigna a un grupo cada una de las entradas de la tabla t
        for (int i = 0; i < t.getNumFilas(); i++) {
            int indice_grupo = calcularGrupo(t.getRowAt(i));
            asignaciones.add(i, indice_grupo);
        }
        return asignaciones;
    }

    private int calcularGrupo(Row fila) {   // Calcula el grupo más cercano a una fila dada
        double min_dist = -1.0;
        int indice_grupo = 0;
        for (int j = 0; j < representantes.size(); j++) {
            List < Double > actual = representantes.get(j);
            if (actual.size() > 0) {
                double dist = metricaEuclidea(fila.getData(), representantes.get(j));
                if (min_dist == -1.0 || min_dist > dist) {
                    min_dist = dist;
                    indice_grupo = j;
                }
            }
        }
        return indice_grupo;
    }

    private Double metricaEuclidea(List < Double > muestra1, List < Double > muestra2) {
        Double sumatorio = 0.0;
        for (int i = 0; i < muestra1.size(); i++) {
            Double resta = (muestra1.get(i) - muestra2.get(i));
            resta = resta * resta;
            sumatorio += resta;
        }
        return (Double) Math.sqrt(sumatorio);
    }

    private void calcularCentroides() {
        List < Integer > puntosporgrupo = new ArrayList < Integer > (representantes.size());
        List < List < Double >> sumapuntos = new ArrayList < List < Double >> (representantes.size());
        for (int i = 0; i < representantes.size(); i++) {   // Inicializa a 0 las listas necesarias para el método
            puntosporgrupo.add(0);
            List < Double > auxiliar = new ArrayList < > (representantes.size());
            for (int j = 0; j < t.getNumColumnas() - 1; j++)
                auxiliar.add(0.);
            sumapuntos.add(auxiliar);
        }
        for (int i = 0; i < t.getNumFilas(); i++) {    // Calcula cuántos puntos hay en cada grupo y la suma de los valores por grupo
            int grupoActual = asignaciones.get(i);
            int puntosActuales = 1 + puntosporgrupo.get(grupoActual);
            puntosporgrupo.set(grupoActual, puntosActuales);
            sumapuntos.set(grupoActual, suma(sumapuntos.get(grupoActual), t.getRowAt(i).getData()));
        }
        for (int i = 0; i < numberClusters; i++) {  // Obtiene la 'media' por puntos de cada grupo
            representantes.set(i, multiplicar(sumapuntos.get(i), (float) 1 / puntosporgrupo.get(i)));
            obtenerEtiqueta(i);
        }
    }

    private List < Double > suma(List < Double > lista1, List < Double > lista2) { // Dadas dos listas de valores hace la suma vectorial
        List < Double > resultado = new ArrayList < Double > (lista1.size());
        for (int i = 0; i < lista1.size(); i++) {
            resultado.add(lista1.get(i) + lista2.get(i));
        }
        return resultado;
    }

    private List < Double > multiplicar(List < Double > lista, double constante) { // Dadas dos listas de valores hace el producto escalar
        List < Double > resultado = new ArrayList < Double > (lista.size());
        for (int i = 0; i < lista.size(); i++) {
            resultado.add(lista.get(i) * constante);
        }
        return resultado;
    }

    private String obtenerEtiqueta(int indiceGrupo) {   // Dado el índice de un grupo, obtiene la etiqueta de uno de sus elementos
        for (int i = 0; i < t.getNumFilas(); i++) {
            if (asignaciones.get(i) == indiceGrupo) {
                return t.getRowAt(i).getLabel();
            }
        }
        return null;
    }

}