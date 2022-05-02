package interfaces;

import mvc.Model;
import mvc.View;

import java.io.FileNotFoundException;
import java.util.List;

public interface ControllerInterface {
    void loadData();
    void estimateParams();
    List<String> getHeader();
    void showLoadedData();
}
