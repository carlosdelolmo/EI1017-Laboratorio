import csv.CSV;
import org.junit.jupiter.api.Test;
import table.TableWithLabels;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TableWithLabelsTest {

    CSV fichero = new CSV();
    String sep = System.getProperty("file.separator");
    String fName = "src"+sep+"main"+sep+ "iris.csv";
    TableWithLabels tabla = (TableWithLabels) fichero.readTableWithLabels(fName);

    TableWithLabelsTest() throws FileNotFoundException {
    }

    @Test
    void getHeader() {
        assertEquals(tabla.getHeader().get(2), "petal length");
    }

    @Test
    void getNumColumnas() {
        assertEquals(5, tabla.getNumColumnas());
    }

    @Test
    void getNumFilas(){
        assertEquals(150, tabla.getNumFilas());
    }

    @Test
    void getRowAt() {
        assertEquals(1.4, tabla.getRowAt(0).getElement(2));
        assertEquals(4.3, tabla.getRowAt(13).getElement(0));
    }

    @Test
    void getColumAt() {
        assertEquals(4.6, tabla.getColumAt(0).get(6));
        assertEquals(0.2, tabla.getColumAt(3).get(0));
    }

    @Test
    void getDatos() {
        assertEquals(1.4, tabla.getDatos().get(0).getData().get(2));
        assertEquals("Iris-setosa", tabla.getRowAt(0).getLabel());
    }
}