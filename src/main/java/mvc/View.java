package mvc;

import distance.DistanceType;
import interfaces.ObserverInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import row.Row;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void paramsAreReady(){}
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

    public void createGUI(Stage primaryStage){
        this.stage = primaryStage;
        createMainView();
        setFileChooserButton();
    }
    private void createMainView(){
        stage.setTitle("KNN");
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

    private void showLoadedData(){/*
        ObservableList<List> dataToShow = FXCollections.observableArrayList(data);
        ListView listView = new ListView<>(dataToShow);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(listView);
        BorderPane.setAlignment(listView, Pos.CENTER);
        stage.setScene(new Scene(borderPane, 300, 300));
        stage.show();*/
        ObservableList headers = FXCollections.observableList(model.getHeaeders());
        ObservableList distances = FXCollections.observableList(Arrays.asList(DistanceType.values()));
        ComboBox ySelection = new ComboBox(headers);
        ComboBox xSelection = new ComboBox(headers);
        ComboBox distanceSelection = new ComboBox(distances);
        TextField pointToEstimate = new TextField("Punto a estimar");
        Label estimationLabel = new Label("Etiqueta de la estimacion");
        Button estimateButton = new Button("Estimar");
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xSelection.getAccessibleText());
        yAxis.setLabel(ySelection.getAccessibleText());
        ScatterChart scatterChart = new ScatterChart(xAxis, yAxis);
       //  scatterChart.getData().add(new XYChart.Data(1.2,3.5));
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(ySelection, 0, 5);
        gridPane.add(scatterChart, 5, 5);
        gridPane.add(xSelection, 10, 10);
        gridPane.add(distanceSelection, 20, 4);
        gridPane.add(pointToEstimate, 20, 5);
        gridPane.add(estimateButton, 20, 6);
        stage.setScene(new Scene(gridPane));
        stage.show();

    }

}
