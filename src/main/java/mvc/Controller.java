package mvc;

import distance.DistanceType;
import interfaces.ControllerInterface;
import interfaces.Distance;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Controller { //ToDo interfaz
    private Model model;
    private View view;
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
        return model;
    }

    public View getObserver() {
        return view;
    }

    public void loadData() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file!=null) {
           model.loadData(file.getAbsolutePath());
        }
    }
    public void estimateParams(String punto){
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
    public List<String> getHeaeder(){
        return model.getHeaeder();
    }
    public void showLoadedData(){
        view.showLoadedData();
    }
    /*
    // ToDo Haría falta este método realmente?
    public void reloadChart(String xValue, String yValue){
        view.reloadChart(xValue, yValue);
    }*/
}
