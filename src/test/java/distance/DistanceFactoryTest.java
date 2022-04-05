package distance;

import csv.CSV;
import interfaces.Distance;
import org.junit.jupiter.api.Test;
import table.Table;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistanceFactoryTest {
    CSV fichero = new CSV();
    String sep = System.getProperty("file.separator");
    String fName = "src"+sep+"main"+sep+ "miles_dollars.csv";
    Table tabla = fichero.readTable(fName);

    DistanceFactoryTest() throws FileNotFoundException {
    }

    @Test
    void getDistance() {
        DistanceFactory factoria = new DistanceFactory();
        Distance distanciaManhattanFactoria = factoria.getDistance(DistanceType.MANHATTAN);
        Distance distanciaEucliedanFactoria = factoria.getDistance(DistanceType.EUCLIDEAN);
        Distance distanciaManhattanReal = new ManhattanDistance();
        Distance distanciaEuclideanReal = new EuclideanDistance();

        for(int i = 0; i < tabla.getNumFilas() - 1; i+= 3){
            List<Double> fila1 = tabla.getRowAt(i).getData();
            List<Double> fila2 = tabla.getRowAt(i+1).getData();
            assertEquals(distanciaManhattanReal.calculateDistance(fila1,fila2),distanciaManhattanFactoria.calculateDistance(fila1,fila2));
            assertEquals(distanciaEuclideanReal.calculateDistance(fila1,fila2),distanciaEucliedanFactoria.calculateDistance(fila1,fila2));
        }
    }
}