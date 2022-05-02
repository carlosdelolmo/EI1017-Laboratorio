package distance;

import interfaces.Distance;
import interfaces.Factory;

public class DistanceFactory implements Factory {
    @Override
    public Distance getDistance(DistanceType distanceType) {
        Distance distance = switch (distanceType) {
            case EUCLIDEAN -> new EuclideanDistance();
            case MANHATTAN -> new ManhattanDistance();
        };
        return distance;
    }
}