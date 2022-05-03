package javaFx;


import javafx.application.Application;
import javafx.stage.Stage;
import mvc.ControllerKNN;
import mvc.ModelKNN;
import mvc.ViewKNN;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ControllerKNN controller = new ControllerKNN();
        ModelKNN model = new ModelKNN();
        ViewKNN view = new ViewKNN(model, controller);
        controller.setModel(model);
        controller.setView(view);
        model.setView(view);
        view.createGUI(primaryStage);
    }
}
