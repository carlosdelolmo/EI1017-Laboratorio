package aIAlgorithms;

import interfaces.Algorithm;
import interfaces.Distance;
import interfaces.DistanceClient;
import row.Row;
import table.Table;
import table.TableWithLabels;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Kmeans implements Algorithm<Table, Row, String > , DistanceClient {
    final private int NUMBER_CLUSTERS;
    final private int ITERATIONS;
    final private long SEED;
    private Distance distance;
    private TableWithLabels t;
    private List < List < Double >> representantes;
    // private List < String > etiquetas;
    private List < Integer > asignaciones;

    public Kmeans(int numberClusters, int iterations, long seed, Distance distance) {
        validarDatosEntrada(numberClusters, iterations);
        this.NUMBER_CLUSTERS = numberClusters;
        this.ITERATIONS = iterations;
        this.SEED = seed;
        this.distance = distance;
    }

    public void train(Table datos) {
        t = (TableWithLabels) datos;
        representantes = obtenerRepresentantes();
        asignaciones = new ArrayList<>(t.getNumFilas());
        for (int i = 0; i < ITERATIONS; i++) {
            asignaciones = asignarAGrupos();
            calcularCentroides();
        }
    }

    public String estimate(Row d) {
        int indiceGrupo = calcularGrupo(d); // asigna a la variable indiceGrupo el numero de grupo del centroide más cercano a d
        return "cluster-" + indiceGrupo;
    }

    private void validarDatosEntrada(int numberClusters, int iterations) {
        if (numberClusters < 1 || iterations < 1) throw new InvalidParameterException();
    }

    private List < List < Double >> obtenerRepresentantes() { // Obtiene aleatoriamente representantes de grupos
        List < List < Double >> representantes = new LinkedList<>();
        Random random = new Random(SEED);
        for (int i = 0; i < NUMBER_CLUSTERS; i++) {
            List<Double> nuevafila;
            do {
                int next = Math.abs(random.nextInt());
                int indice = (next) % t.getNumFilas();
                nuevafila = t.getRowAt(indice).getData();
            }while(representantes.contains(nuevafila));
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

    private int calcularGrupo(Row fila) { // Calcula el grupo más cercano a una fila dada
        double min_dist = -1.0;
        int indice_grupo = 0;
        for (int j = 0; j < representantes.size(); j++) {
            List < Double > actual = representantes.get(j);
            if (actual.size() > 0) {
                double dist = metricaEuclidea(fila.getData(), representantes.get(j));
                if (min_dist > dist || min_dist == -1.0) {
                    min_dist = dist;
                    indice_grupo = j;
                }
            }
        }
        return indice_grupo;
    }

    private Double metricaEuclidea(List < Double > muestra1, List < Double > muestra2) {
        return distance.calculateDistance(muestra1, muestra2);
    }

    private void calcularCentroides() {
        List < Integer > puntosPorGrupo = new ArrayList<>(representantes.size());
        List < List < Double >> sumaPuntos = new ArrayList<>(representantes.size());
        inicializarCalcularCentroides(puntosPorGrupo, sumaPuntos);
        calculaPuntosPorGrupo(puntosPorGrupo, sumaPuntos);
        mediaPuntosPorGrupo(puntosPorGrupo, sumaPuntos);
    }

    private void inicializarCalcularCentroides(List < Integer > puntosPorGrupo, List < List < Double >> sumaPuntos) { // Inicializa a 0 las listas necesarias para el método
        for (int i = 0; i < representantes.size(); i++) {
            puntosPorGrupo.add(0);
            List < Double > auxiliar = new ArrayList < > (representantes.size());
            for (int j = 0; j < t.getNumColumnas() - 1; j++)
                auxiliar.add(0.);
            sumaPuntos.add(auxiliar);
        }
    }

    private void calculaPuntosPorGrupo(List < Integer > puntosPorGrupo, List < List < Double >> sumaPuntos) { // Calcula cuántos puntos hay en cada grupo y la suma de los valores por grupo
        for (int i = 0; i < t.getNumFilas(); i++) {
            int grupoActual = asignaciones.get(i);
            int puntosActuales = 1 + puntosPorGrupo.get(grupoActual);
            puntosPorGrupo.set(grupoActual, puntosActuales);
            sumaPuntos.set(grupoActual, sumar(sumaPuntos.get(grupoActual), t.getRowAt(i).getData()));
        }
    }

    private void mediaPuntosPorGrupo(List < Integer > puntosPorGrupo, List < List < Double >> sumaPuntos) { // Obtiene la 'media' por puntos de cada grupo
        for (int i = 0; i < NUMBER_CLUSTERS; i++) {
            representantes.set(i, multiplicar(sumaPuntos.get(i), (float) 1 / puntosPorGrupo.get(i)));
        }
    }

    private List < Double > sumar(List < Double > lista1, List < Double > lista2) { // Dadas dos listas de valores hace la suma vectorial
        List < Double > resultado = new ArrayList<>(lista1.size());
        for (int i = 0; i < lista1.size(); i++) {
            resultado.add(lista1.get(i) + lista2.get(i));
        }
        return resultado;
    }

    private List < Double > multiplicar(List < Double > lista, double constante) { // Dadas dos listas de valores hace el producto escalar
        List < Double > resultado = new ArrayList<>(lista.size());
        for (Double aDouble : lista) {
            resultado.add(aDouble * constante);
        }
        return resultado;
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}