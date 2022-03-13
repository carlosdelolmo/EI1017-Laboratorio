import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class Table {
    protected List<Row> datos = new LinkedList<>();
    private List<String> header = new LinkedList<String>();
    public Table(){}

    public void addHeader (String[] campos){
        for(int i=0;i< campos.length;i++){
            header.add(campos[i]);
        }
    }

    public void addRow(String[] palabras){
        Row fila = new Row();
        getRowToInsert(palabras, fila);
        datos.add(fila);
    }

    protected void getRowToInsert(String[] palabras, Row fila){
        for(int a=0;a< palabras.length;a++){
            fila.add(palabras[a]);
        }
    }

    public List<Double> getColumAt(int col){
        List<Double> columna= new LinkedList<Double>();
        for(int i=0;i<datos.size();i++){
            columna.add(datos.get(i).getElement(col));
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
