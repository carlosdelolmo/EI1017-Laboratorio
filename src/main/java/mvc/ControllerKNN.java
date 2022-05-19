package mvc;

import distance.DistanceFactory;
import distance.DistanceType;
import distance.EuclideanDistance;
import interfaces.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ControllerKNN implements ControllerInterface {
    private ModelInterface model;
    private ViewInterface view;
    private List<Double> lastPoint;
    private String lastLabel;
    public ControllerKNN(){}
    public ControllerKNN(ModelInterface model, ViewKNN view){
        this.model = model;
        this.view = view;
    }
    public void setView(ViewInterface view){
        this.view = view;
    }
    public void setModel(ModelInterface model) {
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
    public void estimateClass(DistanceType distanceType, String punto){
        DistanceFactory distanceFactory = new DistanceFactory();
        Distance distance = distanceFactory.getDistance(distanceType);
        // String punto = view.getPuntoValue();
        String[] puntoSplit = punto.split(",");
        if(puntoSplit.length == model.getNumColumns() - 1){
            List<Double> listaCoor = new LinkedList<>();
            for (String s : puntoSplit) {
                listaCoor.add(Double.parseDouble(s));
            }
            model.estimateParams(listaCoor, distance);
        }
    }
    @Override
    public List<Double> getLastPoint(){ return lastPoint; }
    @Override
    public void setLastPoint(List<Double> point){ this.lastPoint = point;}
}
