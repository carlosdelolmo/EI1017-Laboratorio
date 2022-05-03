package mvc;

import distance.DistanceFactory;
import interfaces.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ControllerKNN implements ControllerInterface {
    private ModelInterface model;
    private ViewInterface view;
    public ControllerKNN(){}
    public ControllerKNN(ModelKNN model, ViewKNN view){
        this.model = model;
        this.view = view;
    }
    public void setView(ViewKNN view){
        this.view = view;
    }
    public void setModel(ModelKNN model) {
        this.model = model;
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
    public void estimateParams(Distance distance){
        String punto = view.getPuntoValue();
        String[] puntoSplit = punto.split(",");
        // System.out.println(Arrays.stream(puntoSplit).toList().toString());
        if(puntoSplit.length == model.getNumColumns() - 1){
            List<Double> listaCoor = new LinkedList<>();
            for (String s : puntoSplit) {
                listaCoor.add(Double.parseDouble(s));
            }
            model.estimateParams(listaCoor, distance);
        }
    }
}
