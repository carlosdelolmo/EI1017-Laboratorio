package row;

public class RowWithLabel extends Row {
    private String label;

    public RowWithLabel() {}

    public void addLabel(String lab){
        label = lab;
    }

    public String getLabel(){
        return label;
    }

}
