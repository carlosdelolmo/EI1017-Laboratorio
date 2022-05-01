package interfaces;

import aIAlgorithms.KNN;
import mvc.View;

import java.util.Collection;
import java.util.List;

public interface ModelInterface {

    void registerView(View obs);
    void notifyViews();
    List<List<Double>> getData();
    List<Double> getData(int row);
    void loadData(String path);
    void estimateParams(List<Double> puntoAEstimar);


}
