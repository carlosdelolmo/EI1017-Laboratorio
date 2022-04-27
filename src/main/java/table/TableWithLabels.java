package table;

import row.Row;
import row.RowWithLabel;

import java.util.Arrays;
import java.util.List;

public class TableWithLabels extends Table {
    public TableWithLabels(){}

    @Override
    public void addRow(String[] palabras){
        RowWithLabel fila = new RowWithLabel();
        super.getRowToInsert(Arrays.copyOfRange(palabras, 0, palabras.length - 1), fila);
        fila.addLabel(palabras[palabras.length - 1]);
        datos.add(fila);
    }

    @Override
    public List<String> getHeader(){
        return super.getHeader().subList(0, super.getHeader().size()-1);
    }

    public RowWithLabel getRowAt(int index){
        return (RowWithLabel) super.getRowAt(index);
    }
}
