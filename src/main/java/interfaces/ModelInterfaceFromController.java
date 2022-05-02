package interfaces;

import mvc.View;

import java.util.List;

public interface ModelInterfaceFromController {
    void loadData(String path);
    void estimateParams(List<Double> puntoAEstimar);
    int getNumColumns();
    List<String> getHeader();

}
