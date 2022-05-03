package interfaces;

import java.util.List;

public interface ControllerInterface {
    void loadData();
    void estimateParams();
    List<String> getHeader();
    void showLoadedData();
}
