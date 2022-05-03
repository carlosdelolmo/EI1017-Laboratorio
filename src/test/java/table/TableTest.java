package table;

import csv.CSV;
import org.junit.jupiter.api.Test;
import row.Row;
import table.Table;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    CSV fichero = new CSV();
    String sep = System.getProperty("file.separator");
    String fName = "data"+sep+ "miles_dollars.csv";
    Table tabla = fichero.readTable(fName);

    TableTest() throws FileNotFoundException {
    }

    @Test
    void getHeader() {
        assertEquals(tabla.getHeader().get(0), "Miles");
    }

    @Test
    void getNumFilas() {
        assertEquals(tabla.getNumFilas(), 25);
    }

    @Test
    void getNumColumnas() {
        assertEquals(tabla.getNumColumnas(), 2);
    }

    @Test
    void testGetRowAt() {
        Row fila = new Row();
        fila.add("1211");
        fila.add("1802");
        assertEquals(tabla.getRowAt(0).getElement(0),fila.getElement(0));
        assertEquals(tabla.getRowAt(0).getElement(1),fila.getElement(1));
    }

    @Test
    void testGetColumAt() {
        assertEquals(tabla.getColumAt(0).get(0),1211.0);
        assertEquals(tabla.getColumAt(0).get(1), 1345.0);
        assertEquals(tabla.getColumAt(0).get(2), 1422.0);

    }
}