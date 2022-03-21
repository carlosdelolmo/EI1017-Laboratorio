import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KmeansTest {
    CSV fichero = new CSV();
    String sep = System.getProperty("file.separator");
    String fName = "src"+sep+"main"+sep+ "iris.csv";
    TableWithLabels tabla = (TableWithLabels) fichero.readTableWithLabels(fName);
    Kmeans algoritmo = new Kmeans(5,100, (long) 1244325235235.);

    KmeansTest() throws FileNotFoundException {
    }

    @Test
    void train() {

        algoritmo.train(tabla);
    }

    @Test
    void estimate() {
        algoritmo.train(tabla);
        List<Integer> lista = new LinkedList<>();
        lista.add(2); lista.add(56); lista.add(78); lista.add(64); lista.add(120);
        for(int index : lista){
            assertEquals(tabla.getRowAt(index).getLabel(), algoritmo.estimate(tabla.getRowAt(index)));
            // Comprobamos que para 5 elementos de iris.csv, las estimaciones son correctas.
        }
        for(int j = 65; j < 100; j+= 5) {
            RowWithLabel fila = tabla.getRowAt(j);
            RowWithLabel filaMod = new RowWithLabel();
            for (int i = 0; i < fila.getData().size(); i++) {
                filaMod.add(String.valueOf(fila.getElement(i) + 0.1));
            }
            assertEquals(fila.getLabel(), algoritmo.estimate(filaMod));
            // Comprobamos que para 5 elementos cercanos a valores conocidos de iris.csv, las estimaciones son las esperadas.
        }
    }
}