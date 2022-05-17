package aIAlgorithms;

import interfaces.Algorithm;
import interfaces.Distance;
import interfaces.DistanceClient;
import table.Table;
import table.TableWithLabels;

import java.util.List;

public class KNN implements Algorithm<Table,List<Double>,String>, DistanceClient {
    private TableWithLabels tabla;
    private Distance distance;

    public KNN(Distance distancia) {
        this.distance = distancia;
    }
    public void train(Table tabla){
        this.tabla = (TableWithLabels) tabla;
    }

    public String estimate(List<Double> sample){
        if(sample.size() != tabla.getNumColumnas() - 1) return null;
        int posMinimo = minimaDistancia(sample); // Calcula la posición de la mínima distancia
        return tabla.getRowAt(posMinimo).getLabel();
    }

    private int minimaDistancia(List<Double> sample){
        Double minimo = null;
        int posMinimo = 0;
        for(int i = 0; i < tabla.getNumFilas(); i++){
            List<Double> fila = tabla.getRowAt(i).getData();
            double distancia = distance.calculateDistance(sample, fila);
            if(minimo == null) minimo = distancia;
            else if(distancia < minimo){
                minimo = distancia;
                posMinimo = i;
            }
        }
        return posMinimo;
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
