package row;

import java.util.LinkedList;
import java.util.List;

public class Row {
    public Row(){}
    private List<Double> fila = new LinkedList<>();

    public void add(String valor){ fila.add(Double.parseDouble(valor)); }

    public Double getElement(int i){
        if(i >= fila.size() || i < 0) return null;
        return fila.get(i);
    }

    public List<Double> getData(){
        return fila;
    }


}
