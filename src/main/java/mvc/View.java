package mvc;

import interfaces.ObserverInterface;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class View implements ObserverInterface {
    private Model model;
    private Controller controller;
    private Button btnLoad;
    private List<Double> data;
    public View(Model model, Controller controller){
        this.model = model;
        this.controller = controller;
        // model.registerView(this);
    }
    public View(){}
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
            data = model.getData(i);
        }
    }

    public void paramsAreReady() {

    }

    public void disableEstimateParams() {
    }
    public void createGUI(Stage primaryStage){
        createMainView(primaryStage);
        setActions();
        showLoadedData();
    }
    private void createMainView(Stage stage){
        stage.setTitle("KNN");
        StackPane root = new StackPane();
        btnLoad = new Button("Cargar dataset");
        Label descrip = new Label("No data avalible");
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(btnLoad);
        borderPane.setBottom(descrip);
        BorderPane.setAlignment(descrip, Pos.CENTER);
        BorderPane.setMargin(descrip, new Insets(20, 20, 20, 20));
        stage.setScene(new Scene(borderPane, 300, 300));
        stage.show();

    }
    private void setActions(){
        btnLoad.setOnAction(actionEvent -> {
            controller.loadData();
            newDataIsLoaded();
            System.out.println("Filas: " + model.getNumFilas());
        });
    }
    private void showLoadedData(){
    }

}
