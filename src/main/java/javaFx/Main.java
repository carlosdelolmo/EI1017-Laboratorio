package javaFx;


import javafx.application.Application;
import javafx.stage.Stage;
import mvc.Controller;
import mvc.Model;
import mvc.View;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller();
        Model model = new Model();
        View view = new View(model, controller);
        controller.setModel(model);
        controller.setView(view);
        model.setView(view);
        view.createGUI(primaryStage);
    }
}
