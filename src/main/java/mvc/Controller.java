package mvc;

import interfaces.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Controller implements ControllerInterface {
    private ModelInterface model;
    private ViewInterface view;
    public Controller(){}
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }
    public void setView(View view){
        this.view = view;
    }
    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return (Model) model;
    }

    public View getObserver() {
        return (View) view;
    }
    @Override
    public void loadData() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file!=null) {
           model.loadData(file.getAbsolutePath());
        }
    }
    @Override
    public void estimateParams(){
        String punto = view.getPuntoValue();
        String[] puntoSplit = punto.split(",");
        // System.out.println(Arrays.stream(puntoSplit).toList().toString());
        if(puntoSplit.length == model.getNumColumns() - 1){
            List<Double> listaCoor = new LinkedList<>();
            for(int i = 0; i < puntoSplit.length; i++) {
                listaCoor.add(Double.parseDouble(puntoSplit[i]));
            }
            model.estimateParams(listaCoor);
        }
    }
    @Override
    public List<String> getHeader(){
        return model.getHeader();
    }
    @Override
    public void showLoadedData(){
        view.showLoadedData();
    }
}
