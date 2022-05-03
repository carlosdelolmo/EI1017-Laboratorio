package mvc;

import distance.DistanceFactory;
import distance.DistanceType;
import interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ViewKNN implements ViewInterface {
    private ModelInterface model;
    private ControllerInterface controller;
    private Button btnLoad;
    private List<List<Double>> data = new LinkedList<>();
    private int numRows;
    private Label descrip;
    private Stage stage;
    private List<Object> listaInserts = new LinkedList();
    private GridPane gridPane;
    private List<Double> queryPoint;
    private final List<Integer> coorX = Arrays.asList(1,0,2,2,2,2,1); // Coordenadas de la disposicion escogida
    private final List<Integer> coorY = Arrays.asList(2,1,1,2,3,4,1);

    public ViewKNN(ModelKNN model, ControllerKNN controller){
        this.model = model;
        this.controller = controller;
        model.registerView(this);
    }
    public ViewKNN(){}
    public void setModel(ModelKNN model) {
        this.model = model;
        model.registerView(this);
    }

    public void setController(ControllerKNN controller) {
        this.controller = controller;
    }

    @Override
    public void newDataIsLoaded() {
        numRows = model.getNumRows();
        data = model.getData();
        /*
        for(int i = 0; i < model.getNumRows(); i++) {
            data.add(model.getData(i));
        }*/
        // preTableView();
        showLoadedData();
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
    private void preTableView(){
        btnLoad.setText("Ver datos");
        descrip.setText("Filas: " + numRows);
        setGoToTableButton();
    }
    private void setGoToTableButton(){
        btnLoad.setOnAction(actionEvent -> showLoadedData());
    }
    @Override
    public void showLoadedData(){
        List<String> headerList = model.getHeader();
        ObservableList observableHeaderList = FXCollections.observableList(headerList);

        ComboBox xSelection = createComboBox(observableHeaderList, true);
        listaInserts.add(xSelection);

        ComboBox ySelection = createComboBox(observableHeaderList, false);
        listaInserts.add(ySelection);

        ObservableList distances = FXCollections.observableList(Arrays.asList(DistanceType.values()));
        ComboBox distanceSelection = createComboBox(distances, true);
        listaInserts.add(distanceSelection);

        TextField pointToEstimate = new TextField();
        pointToEstimate.setPromptText("1.0, 2.0, 3.0, 4.0");
        listaInserts.add(pointToEstimate);

        Label estimationLabel = new Label("Etiqueta de la estimacion");
        listaInserts.add(estimationLabel);

        Button estimateButton = new Button("Estimar");
        listaInserts.add(estimateButton);

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xSelection.getAccessibleText());

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(ySelection.getAccessibleText());

        ScatterChart scatterChart = createScatterChart(xAxis,yAxis,headerList.get(0), headerList.get(headerList.size()-1));
        listaInserts.add(scatterChart);

        gridPane = createGridPane();

        insertIntoGridPane(gridPane, coorX, coorY, listaInserts);
        // coorX y coorY son unos vectores de enteros que identifican en la posición n, la coordenada 'x' e 'y' en gridPane del elemento n de listaInserts

        insertDataIntoChart(scatterChart, 0, headerList.size()-1);

        setXYChangeButton(xSelection, ySelection);
        setEstimateChangeButton(estimateButton, pointToEstimate.getAccessibleText(),(DistanceType) distanceSelection.getValue());

        stage.setScene(new Scene(gridPane));
        stage.show();
    }
    private GridPane createGridPane(){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }
    private ComboBox createComboBox(ObservableList source, boolean defaultFirst){
        ComboBox resultado = new ComboBox(source);
        if(defaultFirst)
            resultado.getSelectionModel().selectFirst();
        else
            resultado.getSelectionModel().selectLast();
        return resultado;
    }
    private ScatterChart createScatterChart(NumberAxis xAxis, NumberAxis yAxis, String xLabel, String yLabel){
        ScatterChart scatterChart = new ScatterChart(xAxis, yAxis);
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
        modifyChartTitle(scatterChart, xLabel, yLabel);
        return scatterChart;
    }
    private void modifyChartTitle(ScatterChart scatterChart, String xLabel, String yLabel){
        scatterChart.setTitle(xLabel + " vs " + yLabel);
    }
    private void insertIntoGridPane(GridPane gridPane, List<Integer> posX, List<Integer> posY, List<Object> listaInserts){
        for(int a = 0; a < listaInserts.size(); a++){
            gridPane.add((Node) listaInserts.get(a),posX.get(a),posY.get(a));
        }
    }
    private void insertDataIntoChart(ScatterChart scatterChart, int serieXIndex, int serieYIndex){
        // System.out.println("SC: " + scatterChart.toString() + " - X index: " + serieXIndex + " - Y index: " + serieYIndex);
        List<XYChart.Series> seriesList = new LinkedList<>();
        for(int i = 0; i < model.getNumberOfLabels(); i++){     // Creamos tantas series como distintas etiquetas tenga el fichero csv pasado
            seriesList.add(new XYChart.Series());
            seriesList.get(i).setName(model.getLabelFromList(i));
        }
        for(int i = 0; i < data.size(); i++){                   // Insertamos en la serie correspondiente el punto
            int labelIndex = model.getIndexOfLabel(i);
            seriesList.get(labelIndex).getData().add(new XYChart.Data(data.get(i).get(serieXIndex), data.get(i).get(serieYIndex)));
        }
        scatterChart.getData().addAll(seriesList);
        if(queryPoint != null){
            insertEstimationSerie();
        }
    }
    private void setXYChangeButton(ComboBox x, ComboBox y){
        x.setOnAction(actionEvent -> {
            reloadChart(x.getValue().toString(), y.getValue().toString());
        });
        y.setOnAction(actionEvent -> {
            reloadChart(x.getValue().toString(), y.getValue().toString());
        });
    }
    public void reloadChart(String xValue, String yValue){
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xValue);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yValue);

        ScatterChart scatterChart = createScatterChart(xAxis,yAxis,xValue,yValue);
        listaInserts.set(6, scatterChart); // Insertamos en la posición 6 del vector de Inserts el nuevo gráfico
        // System.out.println("X: " + xValue + " - Y: " + yValue);
        insertDataIntoChart(scatterChart, model.getIndexOfHeader(xValue), model.getIndexOfHeader(yValue)); // Le decimos al metodo insertDataIntoChart que queremos
        gridPane = createGridPane();                                                                       // insertar en scatterChart las opciones que tenemos en xValue
        insertIntoGridPane(gridPane, coorX, coorY, listaInserts);                                          // e yValue, pero se lo decimos por medio de índices
        stage.setScene(new Scene(gridPane));
        stage.show();
    }

    public void setEstimateChangeButton(Button button, String textoPunto, DistanceType distance){
        DistanceFactory distanceFactory = new DistanceFactory();
        button.setOnAction(actionEvent -> {
            controller.estimateParams(distanceFactory.getDistance(distance));
        });
    }
    @Override
    public String getPuntoValue(){
        TextField currentLabel = (TextField) listaInserts.get(3);
        return currentLabel.getText();
    }
    @Override
    public void estimationDone(){
        String estimationLabel = model.getEstimationLabel();
        Label currentLabel = (Label) listaInserts.get(4);
        currentLabel.setText("Estimación: " + estimationLabel);
        queryPoint = model.getPunto();
        insertEstimationSerie();
        // insertEstimationIntoChart();
        // System.out.println(estimationLabel);
    }
     private void insertEstimationSerie(){  //ToDo Borrar anterior punto a estimar
        ScatterChart scatterChart = (ScatterChart) listaInserts.get(6);
        XYChart.Series newSerie = new XYChart.Series();
        newSerie.setName("Estimation");
        ComboBox xSelection = (ComboBox) listaInserts.get(0);
        ComboBox ySelection = (ComboBox) listaInserts.get(1);
        // System.out.println(xSelection.getValue().toString());
        Double x = queryPoint.get(model.getIndexOfHeader(xSelection.getValue().toString()));
        Double y = queryPoint.get(model.getIndexOfHeader(ySelection.getValue().toString()));
        newSerie.getData().add(new XYChart.Data(x,y));
        scatterChart.getData().add(newSerie);
    }
}
