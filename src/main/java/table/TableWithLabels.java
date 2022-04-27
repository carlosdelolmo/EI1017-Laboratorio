package table;

import row.Row;
import row.RowWithLabel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableWithLabels extends Table {
    public TableWithLabels(){}
    private List<String> labels;
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
    public int getNumLabel(int i){
        if(labels.isEmpty()) {
            Set<String> knownLabels = new HashSet<>();
            for (int j = 0; j < i; j++) {
                if (!knownLabels.contains(getRowAt(j).getLabel())) {
                    knownLabels.add(getRowAt(j).getLabel());
                    labels.add(getRowAt(j).getLabel());
                }
            }
            return knownLabels.size();
        }
        for(int j = 0; j < labels.size(); j++){
            if(labels.get(j).equals(getRowAt(i)))
                return j;
        }
        return -1;
    }
}
