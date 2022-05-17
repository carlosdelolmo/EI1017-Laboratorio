package aiAlgorithms;

import csv.CSV;
import aIAlgorithms.RegresionLineal;
import org.junit.jupiter.api.Test;
import table.Table;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegresionLinealTest {
    CSV fichero = new CSV();
    String sep = System.getProperty("file.separator");
    String fName = "data"+sep+ "miles_dollars.csv";
    Table tabla = fichero.readTable(fName);
    RegresionLineal regresion = new RegresionLineal() {

    };

    RegresionLinealTest() throws FileNotFoundException {
    }


    @Test
    void train() {
        regresion.train(tabla);
        assertEquals(1.255, regresion.getA(), 0.001);
        assertEquals(274.85, regresion.getB(), 0.001);          // Obtenemos estos valores mediante el calculo manual de la recta de regresion
    }

    @Test
    void estimate() {
        regresion.train(tabla);                                                 // Usamos una propiedad matemática para comprobar el correcto
        assertEquals(regresion.getB(), regresion.estimate(0.0));         // funcionamiento ya que es impreciso hacerlo con valores de las tablas
    }
}