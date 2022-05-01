package mvc;

import aIAlgorithms.KNN;
import csv.CSV;
import distance.EuclideanDistance;
import interfaces.ModelInterface;
import table.TableWithLabels;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class Model implements ModelInterface {
    View view;
    private String estimationLabel;
    private List<Double> punto;
    public Model(){}
    public void setView(View view) {
        this.view = view;
    }
    public View getObserver() {
        return view;
    }
    TableWithLabels table = null;
    List<View> viewList = new LinkedList<>();
    public int getNumRows(){
        return table.getNumFilas();
    }
    public int getNumColumns(){return table.getNumColumnas();}
    @Override
    public void notifyViews(){
        for(View v : viewList){
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
    @Override
    public void registerView(View v){
        viewList.add(v);
    }
    private void trainParams(KNN algorithm){
        algorithm.train(table);
    }
    @Override
    public void estimateParams(List<Double> punto){
        KNN algorithm = new KNN(new EuclideanDistance());
        trainParams(algorithm);
        estimationLabel = algorithm.estimate(punto);
        this.punto = punto;
        estimationDone();
    }
    private void estimationDone(){
        for(View suscrito:viewList){
            view.estimationDone();
        }
    }
    public String getEstimationLabel(){
        return estimationLabel;
    }
    public List<Double> getPunto(){return punto;}
    public List<String> getHeaeder(){
        return table.getHeader();
    }
    public int getIndexOfLabel(int indexOfRow){
        return table.getIndexOfLabel(indexOfRow);
    }
    public int getIndexOfLabel(String label){
        return table.getLabelsList().indexOf(label);
    }
    public String getLabelFromList(int indexInList){
        return table.getLabelsList().get(indexInList);
    }
    public int getNumberOfLabels(){return table.getNumberOfLabels();}
    public int getIndexOfHeader(String header){
        return table.getHeader().indexOf(header);
    }

}
