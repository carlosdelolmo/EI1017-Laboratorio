import csv.CSV;
import distance.EuclideanDistance;
import estimate.KNN;
import interfaces.Distance;
import org.junit.jupiter.api.Test;
import row.RowWithLabel;
import table.TableWithLabels;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KNNTest {

    Distance distance = new EuclideanDistance();
    CSV fichero = new CSV();
    String sep = System.getProperty("file.separator");
    String fName = "src"+sep+"main"+sep+ "iris.csv";
    TableWithLabels tabla = (TableWithLabels) fichero.readTableWithLabels(fName);
    KNN knn = new KNN(distance);

    KNNTest() throws FileNotFoundException {
    }

    @Test
    void train() {
        knn.train(tabla);
    }

    @Test
    void estimate() {
        knn.train(tabla);
        List<Double> lista = new LinkedList<Double>();
        assertEquals(null, knn.estimate(lista)); // Lista vacía

        lista = new LinkedList<>();
        lista.add(1.0); lista.add(2.7); lista.add(5.1);
        assertEquals(null, knn.estimate(lista)); // El numero de columnas no coincide

        lista = new LinkedList<>();
        lista.add(5.1); lista.add(3.4); lista.add(1.5); lista.add(0.2);
        assertEquals("Iris-setosa", knn.estimate(lista));

        List<Integer> listaIndices = new LinkedList<>();
        listaIndices.add(0); listaIndices.add(74); listaIndices.add(109); listaIndices.add(129);
        for(int indice : listaIndices){
            RowWithLabel fila = tabla.getRowAt(indice);
            lista = fila.getData();
            assertEquals(fila.getLabel(), knn.estimate(lista));
        }

        lista = new LinkedList<>();
        lista.add(5.1); lista.add(3.4); lista.add(1.6); lista.add(0.3);
        assertEquals("Iris-setosa", knn.estimate(lista)); // Valores cercanos a la Iris-Setosa anteriormente probada.
    }
}