package interfaces;

import mvc.Observer;

import java.util.List;

public interface ModelInterface {
    void registerObserver(Observer obs);
    int getNumRows();
    void notifyObservers();

    List<Double> getData(int i);
}
