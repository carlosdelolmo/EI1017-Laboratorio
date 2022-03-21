import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    CSV fichero = new CSV();
    String sep = System.getProperty("file.separator");
    String fName = "src"+sep+"main"+sep+ "miles_dollars.csv";
    Table tabla = fichero.readTable(fName);

    TableTest() throws FileNotFoundException {
    }

    @Test
    void getHeader() {
        assertEquals(tabla.getHeader().get(0), "Miles");
    }

    @Test
    void getNumFilas() throws FileNotFoundException {
        assertEquals(tabla.getNumFilas(), 25);
    }

    @Test
    void getNumColumnas() throws FileNotFoundException {
        assertEquals(tabla.getNumColumnas(), 2);
    }

    @Test
    void testGetRowAt() throws FileNotFoundException {
        Row fila = new Row();
        fila.add("1211");
        fila.add("1802");
        assertEquals(tabla.getRowAt(0).getElement(0),fila.getElement(0));
        assertEquals(tabla.getRowAt(0).getElement(1),fila.getElement(1));
    }

    @Test
    void testGetColumAt() throws FileNotFoundException {
        assertEquals(tabla.getColumAt(0).get(0),1211.0);
        assertEquals(tabla.getColumAt(0).get(1), 1345.0);
        assertEquals(tabla.getColumAt(0).get(2), 1422.0);

    }
}