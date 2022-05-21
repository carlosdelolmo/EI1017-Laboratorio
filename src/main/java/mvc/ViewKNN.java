package mvc;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
    private final List<Integer> coorX = Arrays.asList(1,0,2,2,2,2,1,2,2); // Coordenadas de la disposicion escogida
    private final List<Integer> coorY = Arrays.asList(2,1,1,2,3,4,1,0,5);

    public ViewKNN(ModelInterface model, ControllerInterface controller){
        setModel(model);
        setController(controller);
    }
    public ViewKNN(){}
    public void setModel(ModelInterface model) {
        this.model = model;
        model.registerView(this);
    }

    public void setController(ControllerInterface controller) {
        this.controller = controller;
    }

    @Override
    public void newDataIsLoaded() {
        numRows = model.getNumRows();
        data = model.getData();
        showLoadedData();
    }
    public void createGUI(Stage primaryStage){
        this.stage = primaryStage;
        createMainView();
        setFileChooserButton();
    }
    private void createMainView(){
        stage.setTitle("KNN");
        btnLoad = new Button("Cargar dataset propio");
        descrip = new Label("No data avalible");
        Button csvSampleButton = new Button("Usar un ejemplo");
        BorderPane borderPane = new BorderPane();
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(btnLoad);
        //borderPane.setCenter(btnLoad);
        hbox.getChildren().add(csvSampleButton);
        borderPane.setCenter(hbox);
        borderPane.setBottom(descrip);
        BorderPane.setAlignment(descrip, Pos.CENTER);
        BorderPane.setMargin(descrip, new Insets(20, 20, 20, 20));
        Scene scene = new Scene(borderPane, 400, 300);
        setCsvSampleButtonChange(csvSampleButton);
        stage.setScene(scene);
        stage.show();

    }
    private void setCsvSampleButtonChange(Button button){
        button.setOnAction(actionEvent -> {
                button.setVisible(false);
                controller.openDefaultCsv();
        }

        );
    }
    private void setFileChooserButton(){
        btnLoad.setOnAction(actionEvent -> {
            controller.loadData();
        });
    }
    private void preTableView(){
        btnLoad.setText("Ver datos");
        descrip.setText("Filas: " + numRows);
        btnLoad.setOnAction(actionEvent -> showLoadedData());
    }
    @Override
    public void showLoadedData(){
        listaInserts = new LinkedList<>();
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

        Button resetButton = new Button("Cambiar fichero");
        listaInserts.add(resetButton);

        Button lastPoint = new Button("Último punto");
        listaInserts.add(lastPoint);

        gridPane = createGridPane();

        insertIntoGridPane(gridPane, coorX, coorY, listaInserts);
        // coorX y coorY son unos vectores de enteros que identifican en la posición n, la coordenada 'x' e 'y' en gridPane del elemento n de listaInserts

        insertDataIntoChart(scatterChart, 0, headerList.size()-1);

        setResetChangeButton(resetButton);
        setAnteriorPuntoChangeButton(lastPoint);
        setXYChangeButton(xSelection, ySelection);
        setEstimateChangeButton(estimateButton, pointToEstimate.getAccessibleText());
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }
    private void setResetChangeButton(Button button){
        button.setOnAction(actionEvent -> {
            listaInserts = new LinkedList<>();
            queryPoint = null;
            controller.setLastPoint(null);
            createGUI(stage);
        });
    }

    private void setAnteriorPuntoChangeButton(Button button){
        button.setOnAction(actionEvent -> {
            List<Double> lastQueryPoint = controller.getLastPoint();
            if(lastQueryPoint != null && !lastQueryPoint.equals(queryPoint)){
                List<Double> aux = new LinkedList<>();
                aux = queryPoint;
                queryPoint = lastQueryPoint;
                controller.setLastPoint(aux);
                String point = queryPoint.toString();
                String accessiblePoint = point.substring(1,point.length()-1);
                callEstimate(accessiblePoint);
                TextField pointText = (TextField) listaInserts.get(3);
                pointText.setText(accessiblePoint);
            }
        });
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
            // System.out.println("Inserting a =" + a + " value: "+ listaInserts.get(a) + " size: " + listaInserts.size());
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

    public void setEstimateChangeButton(Button button, String textoPunto){
        button.setOnAction(actionEvent -> {
            callEstimate(getPuntoValue());
        });
    }
    private void callEstimate(String puntoValue){
        ComboBox distanceSelected = (ComboBox) listaInserts.get(2);
        DistanceType distance = (DistanceType) distanceSelected.getValue();
        controller.estimateClass(distance, puntoValue);
    }
    private String getPuntoValue(){
        TextField currentLabel = (TextField) listaInserts.get(3);
        return currentLabel.getText();
    }
    @Override
    public void estimationDone(){
        String estimationLabel = model.getEstimationLabel();
        Label currentLabel = (Label) listaInserts.get(4);
        currentLabel.setText("Estimación: " + estimationLabel);
        List<Double> aux = queryPoint;
        queryPoint = model.getPunto();
        if(aux != null && !aux.equals(queryPoint)) controller.setLastPoint(aux);
        queryPointIsReady();
    }
    private void queryPointIsReady(){
        ComboBox xHeader = (ComboBox) listaInserts.get(0);
        ComboBox yHeader = (ComboBox) listaInserts.get(1);
        String xSelection = xHeader.getValue().toString();
        String ySelection = yHeader.getValue().toString();
        reloadChart(xSelection, ySelection);
    }
     private void insertEstimationSerie(){
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

    @Override
    public void showCsvPopup(){
        Stage popupCsv = new Stage();
        popupCsv.initModality(Modality.APPLICATION_MODAL);
        popupCsv.initOwner(stage);
        VBox vBox = new VBox(10);
        vBox.setSpacing(10);
        Button button = new Button("Ok");
        button.setOnAction(actionEvent -> popupCsv.close());
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(new Label("Debes usar un fichero csv!"));
        vBox.getChildren().add(button);
        Scene dialogScene = new Scene(vBox, 200, 100);
        popupCsv.setScene(dialogScene);
        popupCsv.show();
    }

    @Override
    public void showInvalidPointPopup(){
        Stage popupPoint = new Stage();
        popupPoint.initModality(Modality.APPLICATION_MODAL);
        popupPoint.initOwner(stage);
        VBox vBox = new VBox(10);
        vBox.setSpacing(10);
        Button button = new Button("Ok");
        button.setOnAction(actionEvent -> popupPoint.close());
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(new Label("Introduce el punto correctamente\nEjemplo de uso: 1.0, 1.2, 2.1, 1.6"));
        vBox.getChildren().add(button);
        Scene dialogScene = new Scene(vBox, 300, 150);
        popupPoint.setScene(dialogScene);
        popupPoint.show();
    }
}