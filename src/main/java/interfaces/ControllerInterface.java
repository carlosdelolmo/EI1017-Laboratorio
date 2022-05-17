package interfaces;

import distance.DistanceType;

public interface ControllerInterface {
    void loadData();
    void estimateClass(DistanceType distance, String punto);
}
