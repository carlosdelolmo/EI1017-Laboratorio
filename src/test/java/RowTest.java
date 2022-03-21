import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RowTest {
    Row fila = new Row();
    @Test
    void add() {
        fila.add("2");
        fila.add("25");
        assertEquals(2.0, fila.getElement(0));
    }

    @Test
    void getElement() {
        assertNull(fila.getElement(0));
        fila.add("3");
        assertEquals(3.0, fila.getElement(0));
    }

    @Test
    void getData() {
        Set<Double> setElementos = new HashSet<>();
        for(int i = 0; i < 50; i += 3){
            setElementos.add((double) i);
            fila.add(String.valueOf(i));
        }
        fila.getData().containsAll(setElementos);
    }
}