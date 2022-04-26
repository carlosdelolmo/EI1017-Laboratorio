package javaFx;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        StackPane root = new StackPane();
        Button btn = new Button("Hola");
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 250, 250));
        primaryStage.show();
    }
}
