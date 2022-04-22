package MVC;

import aIAlgorithms.KNN;
import csv.CSV;
import distance.EuclideanDistance;
import interfaces.ModelInterface;
import table.Table;
import table.TableWithLabels;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class Model implements ModelInterface {
    Observer observer;
    KNN algorithm = new KNN(new EuclideanDistance());
    TableWithLabels table = null;
    List<Observer> observerList = new LinkedList<Observer>();
    public int getNumRows(){
        return table.getNumFilas();
    }
    public void notifyObservers(){
        for(Observer obs : observerList){
            obs.newDataIsLoaded();
        }
    }

    @Override
    public List<Double> getData(int i) {
        return table.getRowAt(i).getData();
    }

    public void loadData(String path) throws FileNotFoundException {
        CSV csv = new CSV();
        table = (TableWithLabels) csv.readTable(path);
        notifyObservers();
    }
    public void registerObserver(Observer obs){
        observerList.add(obs);
    }
    public void estimateParams(){
        algorithm.train(table);
        // Algo más que nos permita estimar los KNN
    }
}
