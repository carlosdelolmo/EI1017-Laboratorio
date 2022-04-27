package mvc;

import distance.DistanceType;
import interfaces.ObserverInterface;
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

import javax.swing.plaf.basic.BasicCheckBoxUI;
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

    private void showLoadedData(){
        List<Object> listaInserts = new LinkedList();
        List<String> headerList = controller.getHeaeder();
        ObservableList observableHeaderList = FXCollections.observableList(headerList);

        ComboBox xSelection = createComboBox(observableHeaderList, true);
        listaInserts.add(xSelection);

        ComboBox ySelection = createComboBox(observableHeaderList, false);
        listaInserts.add(ySelection);

        ObservableList distances = FXCollections.observableList(Arrays.asList(DistanceType.values()));
        ComboBox distanceSelection = createComboBox(distances, true);

        listaInserts.add(distanceSelection);

        TextField pointToEstimate = new TextField("Punto a estimar");
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

        List<Integer> coorX = Arrays.asList(1,0,2,2,2,2,1);
        List<Integer> coorY = Arrays.asList(2,1,1,2,3,4,1);
        GridPane gridPane = createGridPane();

        insertIntoGridPane(gridPane, coorX, coorY, listaInserts);

        insertDataIntoChart(scatterChart);

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
        /*// Esta solución de bajo nos permitía ahorrarnos la lista posX, ya que observamos que podíamos obtener esos datos
          // a partir de un bucle anidado, pero nos obligaba a seguir ese algoritmo en caso de añadir nuevos objetos.
        int a = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j <= i; j++){
                gridPane.add((Node) listaInserts.get(a),i,posY.get(a++));
            }
        }*/
    }
    private void insertDataIntoChart(ScatterChart scatterChart){
        // XYChart.Series series1 = new XYChart.Series();
        // series1.setName("Datos");
        List<XYChart.Series> seriesList = new LinkedList<>();
        for(int i = 0; i < model.getNumberOfLabels(); i++){
            seriesList.add(new XYChart.Series());
            seriesList.get(i).setName(model.getLabelFromList(i));
        }
        // series1.getData().add(new XYChart.Data(4.2, 193.2));
        // scatterChart.getData().add(series1);
        for(int i = 0; i < data.size(); i++){
            // series1.getData().add(new XYChart.Data(data.get(i).get(0), data.get(i).get(1)));
            int labelIndex = model.getIndexOfLabel(i);
            seriesList.get(labelIndex).getData().add(new XYChart.Data(data.get(i).get(0), data.get(i).get(3)));
        }
        scatterChart.getData().addAll(seriesList);

    }
}
