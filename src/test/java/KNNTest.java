import aIAlgorithms.KNN;
import csv.CSV;
import distance.EuclideanDistance;
import distance.ManhattanDistance;
import interfaces.Distance;
import org.junit.jupiter.api.Test;
import row.RowWithLabel;
import table.TableWithLabels;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class KNNTest {
    CSV fichero = new CSV();
    String sep = System.getProperty("file.separator");
    String fName = "src"+sep+"main"+sep+ "iris.csv";
    TableWithLabels tabla = (TableWithLabels) fichero.readTableWithLabels(fName);
    Distance euclideanDistance = new EuclideanDistance();
    Distance manhattanDistance = new ManhattanDistance();
    KNN euclideanKnn = new KNN(euclideanDistance);
    KNN manhattanKnn = new KNN(manhattanDistance);

    KNNTest() throws FileNotFoundException {
    }

    @Test
    void train() {
        euclideanKnn.train(tabla);
        manhattanKnn.train(tabla);
    }

    @Test
    void estimate() {
        euclideanKnn.train(tabla);
        manhattanKnn.train(tabla);
        List<Double> lista = new LinkedList<>();
        assertNull(euclideanKnn.estimate(lista)); // Lista vacía

        lista = new LinkedList<>();
        lista.add(1.0); lista.add(2.7); lista.add(5.1);
        assertNull(euclideanKnn.estimate(lista)); // El numero de columnas no coincide

        lista = new LinkedList<>();
        lista.add(5.1); lista.add(3.4); lista.add(1.5); lista.add(0.2);
        assertEquals("Iris-setosa", euclideanKnn.estimate(lista));

        List<Integer> listaIndices = new LinkedList<>();
        listaIndices.add(0); listaIndices.add(74); listaIndices.add(109); listaIndices.add(129);
        for(int indice : listaIndices){
            RowWithLabel fila = tabla.getRowAt(indice);
            lista = fila.getData();
            assertEquals(fila.getLabel(), euclideanKnn.estimate(lista));
            assertEquals(fila.getLabel(), manhattanKnn.estimate(lista));
        }

        lista = new LinkedList<>();
        lista.add(5.1); lista.add(3.4); lista.add(1.6); lista.add(0.3);
        assertEquals("Iris-setosa", euclideanKnn.estimate(lista));
        assertEquals("Iris-setosa", manhattanKnn.estimate(lista)); // Valores cercanos a la Iris-Setosa anteriormente probada.

    }
}