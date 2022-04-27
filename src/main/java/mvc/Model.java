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
    public Model(){}
    public void setView(View view) {
        this.view = view;
    }
    public View getObserver() {
        return view;
    }
    KNN algorithm = new KNN(new EuclideanDistance());
    TableWithLabels table = null;
    List<View> viewList = new LinkedList<>();
    public int getNumRows(){
        return table.getNumFilas();
    }
    public void notifyViews(){
        for(View v : viewList){
            v.newDataIsLoaded();
        }
    }
    public int getNumFilas(){return table.getNumFilas();}

    @Override
    public List<Double> getData(int i) {
        return table.getRowAt(i).getData();
    }

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
    public void registerView(View v){
        viewList.add(v);
    }
    public void estimateParams(){
        algorithm.train(table);
        // Algo más que nos permita estimar los KNN
    }
    public List<String> getHeaeder(){
        return table.getHeader();
    }
    public int getIndexOfLabel(int indexOfRow){
        return table.getIndexOfLabel(indexOfRow);
    }
    public String getLabelFromList(int indexInList){
        return table.getLabelsList().get(indexInList);
    }
    public int getNumberOfLabels(){return table.getNumberOfLabels();}

}
