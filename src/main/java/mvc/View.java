package mvc;

import interfaces.ObserverInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class View implements ObserverInterface {
    private Model model;
    private Controller controller;
    private Button btnLoad;
    private List<List<Double>> data = new LinkedList<>();
    private int numRows;
    private Label descrip;
    private Stage stage;
    public View(Model model, Controller controller){
        this.model = model;
        this.controller = controller;
        model.registerView(this);
    }
    public View(){}
    public void setModel(Model model) {
        this.model = model;
        model.registerView(this);
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
        numRows = model.getNumRows();
        for(int i = 0; i < model.getNumRows(); i++) {
            data.add(model.getData(i));
        }
        modifyMainView();
    }

    public void paramsAreReady() {

    }

    public void disableEstimateParams() {
    }
    public void createGUI(Stage primaryStage){
        this.stage = primaryStage;
        createMainView();
        setFileChooserButton();
    }
    private void createMainView(){
        stage.setTitle("KNN");
        StackPane root = new StackPane();
        btnLoad = new Button("Cargar dataset");
        descrip = new Label("No data avalible");
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(btnLoad);
        borderPane.setBottom(descrip);
        BorderPane.setAlignment(descrip, Pos.CENTER);
        BorderPane.setMargin(descrip, new Insets(20, 20, 20, 20));
        stage.setScene(new Scene(borderPane, 300, 300));
        stage.show();

    }
    private void setFileChooserButton(){
        btnLoad.setOnAction(actionEvent -> {
            controller.loadData();
        });
    }
    private void modifyMainView(){
        btnLoad.setText("Ver datos");
        descrip.setText("Filas: " + numRows);
        setChangeStageButton();
    }
    private void setChangeStageButton(){
        btnLoad.setOnAction(actionEvent -> showLoadedData());
    }
    private void showLoadedData(){
        ObservableList<List> dataToShow = FXCollections.observableArrayList(data);
        ListView listView = new ListView<>(dataToShow);
        StackPane root = new StackPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(listView);
        BorderPane.setAlignment(listView, Pos.CENTER);
        stage.setScene(new Scene(borderPane, 300, 300));
        stage.show();
    }

}
