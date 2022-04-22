package mvc;

import interfaces.ObserverInterface;

import java.util.List;

public class Observer implements ObserverInterface {
    Model model;
    Controller controller;
    public Observer(Model model, Controller controller){
        this.model = model;
        this.controller = controller;
    }
    public void setModel(Model model) {
        this.model = model;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Model getModel() {
        return model;
    }

    public Controller getController() {
        return controller;
    }
    public void newDataIsLoaded() {
        for(int i = 0; i < model.getNumRows(); i++){
            List<Double> data = model.getData(i);
        }
    }

    public void paramsAreReady() {

    }

    public void disableEstimateParams() {
    }
}
