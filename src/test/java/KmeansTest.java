import csv.CSV;
import distance.EuclideanDistance;
import distance.ManhattanDistance;
import aIAlgorithms.Kmeans;
import interfaces.Distance;
import org.junit.jupiter.api.Test;
import row.Row;
import row.RowWithLabel;
import table.TableWithLabels;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KmeansTest {
    CSV fichero = new CSV();
    String sep = System.getProperty("file.separator");
    String fName = "src"+sep+"main"+sep+ "iris.csv";
    TableWithLabels tabla = (TableWithLabels) fichero.readTableWithLabels(fName);
    Distance euclideanDistance = new EuclideanDistance();
    Distance manhattanDistance = new ManhattanDistance();
    Kmeans euclideanKmeans = new Kmeans(3,25, (long) 235 , euclideanDistance);
    Kmeans manhattanKmeans = new Kmeans(3,25, (long) 235. , manhattanDistance);


    KmeansTest() throws FileNotFoundException {
    }

    @Test
    void train() {
        manhattanKmeans.train(tabla);
        euclideanKmeans.train(tabla);
    }

    @Test
    void estimate() {
        euclideanKmeans.train(tabla);
        manhattanKmeans.train(tabla);

        List<Integer> lista = new LinkedList<>();
        lista.add(2); lista.add(5); lista.add(30); lista.add(22); lista.add(27);
        for(int i = 1; i < lista.size(); i++){
            assertEquals(euclideanKmeans.estimate(tabla.getRowAt(i-1)), euclideanKmeans.estimate(tabla.getRowAt(i)));
            // Comprobamos que para 5 elementos de iris.csv, las estimaciones son correctas.
        }
        for(int j = 65; j < 150; j+= 5) {
            RowWithLabel fila = tabla.getRowAt(j);
            RowWithLabel filaMod = new RowWithLabel();
            for (int i = 0; i < fila.getData().size(); i++) {
                filaMod.add(String.valueOf(fila.getElement(i) + 0.2));
            }
            assertEquals(euclideanKmeans.estimate(fila), euclideanKmeans.estimate(filaMod));
            // Comprobamos que para ciertos elementos cercanos a valores conocidos de iris.csv, las estimaciones son las esperadas.
        }
        List<String> listaEuclidea = new LinkedList<>();
        List<String> listaManhattan = new LinkedList<>();
        for(int j = 35; j < 120; j+= 1) {
            Row fila = tabla.getRowAt(j);
            listaEuclidea.add(euclideanKmeans.estimate(fila));
            listaManhattan.add(manhattanKmeans.estimate(fila));
            // Comprobamos que para ciertos elementos cercanos a valores conocidos de iris.csv, las estimaciones son distintas dependiendo del algoritmo de distancia usado.
        }
        assertNotEquals(listaEuclidea,listaManhattan);
    }
}