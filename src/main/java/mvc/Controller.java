package mvc;

import distance.DistanceType;
import interfaces.ControllerInterface;
import interfaces.Distance;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class Controller implements ControllerInterface {
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
           model.estimateParams();
       }
    }
    public void estimateParams(){
        model.estimateParams();
    }
    public List<String> getHeaeder(){
        return model.getHeaeder();
    }
    public void showLoadedData(){
        view.showLoadedData();
    }
    public void reloadChart(String xValue, String yValue){
        view.reloadChart(xValue, yValue);
    }
}
