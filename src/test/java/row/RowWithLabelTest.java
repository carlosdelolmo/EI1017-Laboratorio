package row;

import org.junit.jupiter.api.Test;
import row.RowWithLabel;

import static org.junit.jupiter.api.Assertions.*;

class RowWithLabelTest {
    RowWithLabel fila = new RowWithLabel();
    @Test
    void add() {
        fila.add("2");
        fila.add("3");
        assertTrue(fila.getData().contains(2.0));
    }

    @Test
    void getElement() {
        fila.add("2");
        fila.add("3");
        assertEquals(2.0,fila.getElement(0));
    }

    @Test
    void addLabel() {
        fila.addLabel("Etiqueta de prueba");
        assertEquals("Etiqueta de prueba",fila.getLabel());
    }

    @Test
    void getLabel() {
        assertNull(fila.getLabel());
        fila.addLabel("Etiqueta de prueba");
        assertEquals("Etiqueta de prueba",fila.getLabel());
    }
}