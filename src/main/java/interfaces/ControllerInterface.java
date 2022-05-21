package interfaces;

import distance.DistanceType;

import java.util.List;

public interface ControllerInterface {
    void loadData();
    void estimateClass(DistanceType distance, String punto);
    List<Double> getLastPoint();
    void setLastPoint(List<Double> point);
    void openDefaultCsv();
    String getSampleFile();
}
