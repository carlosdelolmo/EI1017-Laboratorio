package estimate;

import distance.EuclideanDistance;
import interfaces.Algorithm;
import interfaces.Distance;
import table.TableWithLabels;

import java.util.List;

public class KNN implements Algorithm<TableWithLabels,List<Double>,String> {
    private TableWithLabels tabla;
    private Distance distance;
    public KNN(){}
    public KNN(Distance distance) {
        this.distance = distance;
    }
    public void train(TableWithLabels tablaAEstudiar){
        tabla = tablaAEstudiar;
    }

    public String estimate(List<Double> sample){
        if(sample.size() != tabla.getNumColumnas() - 1) return null;
        int posMinimo = indiceMinimo(sample); // Calcula la posición de la mínima distancia
        return tabla.getRowAt(posMinimo).getLabel();
    }

    private int indiceMinimo(List<Double> sample){
        Double minimo = null;
        int fila = 0;
        for(int i = 0; i < tabla.getNumFilas(); i++){
            List<Double> elementoFichero = tabla.getRowAt(i).getData();
            Double distancia = metricaEuclidea(sample, elementoFichero);
            if(minimo == null) minimo = distancia;
            else if(distancia < minimo){
                minimo = distancia;
                fila = i;
            }
        }
        return fila;
    }
    private Double metricaEuclidea(List<Double> nuevaMuestra, List<Double> elementoFichero){
        return distance.calculateDistance(nuevaMuestra, elementoFichero);
    }
}
