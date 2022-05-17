package distance;

import interfaces.Distance;
import interfaces.Factory;

public class DistanceFactory implements Factory {
    @Override
    public Distance getDistance(DistanceType distanceType) {
        Distance distance = null;
        switch(distanceType){
            case EUCLIDEAN: distance = new EuclideanDistance();
            break;
            case MANHATTAN: distance = new ManhattanDistance();
        }
        return distance;
    }
}