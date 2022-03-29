package table;

import row.Row;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Table {
    protected List<Row> datos = new LinkedList<>();
    private List<String> header = new LinkedList<>();
    public Table(){}

    public void addHeader (String[] campos){
        Collections.addAll(header, campos);
    }

    public void addRow(String[] palabras){
        Row fila = new Row();
        getRowToInsert(palabras, fila);
        datos.add(fila);
    }

    protected void getRowToInsert(String[] palabras, Row fila){
        for (String palabra : palabras) {
            fila.add(palabra);
        }
    }

    public List<Double> getColumAt(int col){
        List<Double> columna= new LinkedList<>();
        for (Row dato : datos) {
            columna.add(dato.getElement(col));
        }
        return columna;
    }

    public void addDato(Row fila){
        datos.add(fila);
    }

    public List<String> getHeader(){
        return header;
    }

    public int getNumFilas(){ return datos.size(); }

    public int getNumColumnas(){
        return header.size();
    }

    public Row getRowAt(int i){
        return datos.get(i);
    }

    public List<Row> getDatos() {
        return datos;
    }

}
