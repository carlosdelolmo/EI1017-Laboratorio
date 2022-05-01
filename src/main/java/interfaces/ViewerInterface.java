package interfaces;

import javafx.stage.Stage;
import mvc.Controller;
import mvc.Model;

public interface ViewerInterface {
    void setController(Controller controller);
    void setModel(Model model);
    void newDataIsLoaded();
    void createGUI(Stage stage);
}
