package interfaces;

import java.io.FileNotFoundException;

public interface ControllerInterface {
    void loadData() throws FileNotFoundException;
    void estimateParams();
}
