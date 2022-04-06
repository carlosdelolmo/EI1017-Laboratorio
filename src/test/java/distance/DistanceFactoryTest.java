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
        Distance manhattanFactoria = factoria.getDistance(DistanceType.MANHATTAN);
        Distance euclideanFactoria = factoria.getDistance(DistanceType.EUCLIDEAN);
        Distance manhattanReal = new ManhattanDistance();
        Distance euclideanReal = new EuclideanDistance();
        // Inicializamos distancias creadas por factoría y otras creadas explícitamente con el costructor de la clase

        for(int i = 0; i < tabla.getNumFilas() - 1; i+= 5){
            List<Double> fila1 = tabla.getRowAt(i).getData();
            List<Double> fila2 = tabla.getRowAt(i+1).getData();

            double distManhattanObtenida = manhattanFactoria.calculateDistance(fila1, fila2);
            double distManhattanEsperada = manhattanReal.calculateDistance(fila1, fila2);
            double distEuclideanObtenida = euclideanFactoria.calculateDistance(fila1, fila2);
            double distEuclideanEsperada = euclideanReal.calculateDistance(fila1, fila2);
            // Obtenemos las distancias entre distintas filas de un fichero CSV y las guardamos en variables.
            // Después comparamos que realmente obtenemos el mismo valor usando la factoría o usando el constructor explícito de la distancia.

            assertEquals(distManhattanEsperada, distManhattanObtenida);
            assertEquals(distEuclideanEsperada, distEuclideanObtenida);
        }
    }
}