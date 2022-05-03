package interfaces;

import java.util.List;

public interface ModelInterface {
    void loadData(String path);
    void estimateParams(List<Double> puntoAEstimar, Distance distance);
    int getNumColumns();
    List<String> getHeader();
    List<List<Double>> getData();
    List<Double> getData(int row);
    int getNumRows();
    int getNumberOfLabels();
    String getLabelFromList(int i);
    int getIndexOfLabel(int numberOfRow);
    int getIndexOfLabel(String Label);
    int getIndexOfHeader(String header);
    String getEstimationLabel();
    List<Double> getPunto();
}
