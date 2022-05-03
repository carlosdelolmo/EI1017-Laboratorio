package mvc;

import aIAlgorithms.KNN;
import csv.CSV;
import interfaces.*;
import table.TableWithLabels;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class ModelKNN implements ModelInterface {
    private ViewInterface view;
    private String estimationLabel;
    private List<Double> punto;
    public ModelKNN(){}
    public void setView(ViewKNN view) {
        this.view = view;
    }
    public ViewKNN getObserver() {
        return (ViewKNN) view;
    }
    TableWithLabels table = null;
    List<ViewKNN> viewList = new LinkedList<>();
    @Override
    public int getNumRows(){
        return table.getNumFilas();
    }
    public int getNumColumns(){return table.getNumColumnas();}
    public void notifyViews(){
        for(ViewKNN v : viewList){
            v.newDataIsLoaded();
        }
    }
    @Override
    public List<Double> getData(int i) {
        return table.getRowAt(i).getData();
    }

    @Override
    public List<List<Double>> getData(){
        List<List<Double>> data = new LinkedList<>();
        for(int i = 0; i < table.getNumFilas(); i++){
            data.add(getData(i));
        }
        return data;
    }
    @Override
    public void loadData(String path) {
        // System.out.println("Ruta: " + path);
        CSV csv = new CSV();
        try {
            table = (TableWithLabels) csv.readTableWithLabels(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        notifyViews();
    }
    public void registerView(ViewKNN v){
        viewList.add(v);
    }
    private void trainParams(KNN algorithm){
        algorithm.train(table);
    }
    @Override
    public void estimateParams(List<Double> punto, Distance distance){
        KNN algorithm = new KNN(distance);
        trainParams(algorithm);
        estimationLabel = algorithm.estimate(punto);
        this.punto = punto;
        estimationDone();
    }
    private void estimationDone(){
        for(ViewKNN suscrito:viewList){
            view.estimationDone();
        }
    }
    @Override
    public String getEstimationLabel(){
        return estimationLabel;
    }
    @Override
    public List<Double> getPunto(){return punto;}
    public List<String> getHeader(){
        return table.getHeader();
    }
    @Override
    public int getIndexOfLabel(int indexOfRow){
        return table.getIndexOfLabel(indexOfRow);
    }
    @Override
    public int getIndexOfLabel(String label){
        return table.getLabelsList().indexOf(label);
    }
    @Override
    public String getLabelFromList(int indexInList){
        return table.getLabelsList().get(indexInList);
    }
    @Override
    public int getNumberOfLabels(){return table.getNumberOfLabels();}
    @Override
    public int getIndexOfHeader(String header){
        return table.getHeader().indexOf(header);
    }

}
