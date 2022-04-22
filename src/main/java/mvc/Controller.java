package mvc;

import interfaces.ControllerInterface;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemNotFoundException;

public class Controller implements ControllerInterface {
    private Model model;
    private Observer observer;
    public Controller(Model model, Observer observer){
        this.model = model;
        this.observer = observer;
    }
    public void setObserver(Observer observer){
        this.observer = observer;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public Observer getObserver() {
        return observer;
    }
    public void loadData() throws FileNotFoundException {
        final JFileChooser fileChooser = null;
        File file = null;
        int fileId = fileChooser.showOpenDialog(null);
        if(fileId == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
        }else{
            throw new FileSystemNotFoundException();
        }
        model.loadData(file.getAbsolutePath());
    }
    public void estimateParams(){
        model.estimateParams();
        observer.disableEstimateParams();
    }
}
