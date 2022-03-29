import csv.CSV;
import distance.EuclideanDistance;
import distance.ManhattanDistance;
import estimate.Kmeans;
import interfaces.Distance;
import org.junit.jupiter.api.Test;
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
    Distance distanceEuclidean = new EuclideanDistance();
    Distance distanceManhattan = new ManhattanDistance();
    Kmeans algoritmoEuclidea = new Kmeans(3,25, (long) 235 , distanceEuclidean);
    Kmeans algoritmoManhattan = new Kmeans(3,25, (long) 235. , distanceManhattan);


    KmeansTest() throws FileNotFoundException {
    }

    @Test
    void train() {
        algoritmoManhattan.train(tabla);
        algoritmoEuclidea.train(tabla);
    }

    @Test
    void estimate() {
        algoritmoEuclidea.train(tabla);
        algoritmoManhattan.train(tabla);

        List<Integer> lista = new LinkedList<>();
        lista.add(2); lista.add(5); lista.add(30); lista.add(22); lista.add(27);
        for(int i = 1; i < lista.size(); i++){
            assertEquals(algoritmoEuclidea.estimate(tabla.getRowAt(i-1)), algoritmoEuclidea.estimate(tabla.getRowAt(i)));
            // Comprobamos que para 5 elementos de iris.csv, las estimaciones son correctas.
        }
        for(int j = 65; j < 150; j+= 5) {
            RowWithLabel fila = tabla.getRowAt(j);
            RowWithLabel filaMod = new RowWithLabel();
            for (int i = 0; i < fila.getData().size(); i++) {
                filaMod.add(String.valueOf(fila.getElement(i) + 0.2));
            }
            assertEquals(algoritmoEuclidea.estimate(fila), algoritmoEuclidea.estimate(filaMod));
            // Comprobamos que para 5 elementos cercanos a valores conocidos de iris.csv, las estimaciones son las esperadas.
        }
        List<String> listaEuclidea = new LinkedList<>();
        List<String> listaManhattan = new LinkedList<>();
        for(int j = 35; j < 120; j+= 1) {
            RowWithLabel fila = tabla.getRowAt(j);
            listaEuclidea.add(algoritmoEuclidea.estimate(fila));
            listaManhattan.add(algoritmoManhattan.estimate(fila));
            // Comprobamos que para 5 elementos cercanos a valores conocidos de iris.csv, las estimaciones son las esperadas.
        }
        // System.out.println(listaEuclidea);
        // System.out.println(listaManhattan);
        assertNotEquals(listaEuclidea,listaManhattan);
    }
}