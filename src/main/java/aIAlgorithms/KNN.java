package aIAlgorithms;

import interfaces.Algorithm;
import interfaces.Distance;
import interfaces.DistanceClient;
import table.TableWithLabels;

import java.util.List;

public class KNN implements Algorithm<TableWithLabels,List<Double>,String>, DistanceClient {
    private TableWithLabels tabla;
    private Distance distance;

    public KNN(Distance distancia) {
        this.distance = distancia;
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
            Double distancia = distance.calculateDistance(sample, elementoFichero);
            if(minimo == null) minimo = distancia;
            else if(distancia < minimo){
                minimo = distancia;
                fila = i;
            }
        }
        return fila;
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
