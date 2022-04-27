package table;

import row.RowWithLabel;

import java.util.*;

public class TableWithLabels extends Table {
    public TableWithLabels(){}
    private List<String> labelsList = new LinkedList<>();
    @Override
    public void addRow(String[] palabras){
        RowWithLabel fila = new RowWithLabel();
        super.getRowToInsert(Arrays.copyOfRange(palabras, 0, palabras.length - 1), fila);
        String label = palabras[palabras.length - 1];
        fila.addLabel(label);
        if(!labelsList.contains(label))
            labelsList.add(label);
        datos.add(fila);
    }

    @Override
    public List<String> getHeader(){
        return super.getHeader().subList(0, super.getHeader().size()-1);
    }

    public RowWithLabel getRowAt(int index){
        return (RowWithLabel) super.getRowAt(index);
    }

    public int getIndexOfLabel(int indexInTable){
        return labelsList.indexOf(getRowAt(indexInTable).getLabel());
    }
    public int getNumberOfLabels(){return labelsList.size();}
    public List<String> getLabelsList(){
        return labelsList;
    }
}
