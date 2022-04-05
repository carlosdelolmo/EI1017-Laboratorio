package interfaces;

import distance.DistanceType;

public interface Factory {
    Distance getDistance(DistanceType distanceType);
}
