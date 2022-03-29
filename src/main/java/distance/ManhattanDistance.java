package distance;

import interfaces.Distance;

import java.util.List;

public class ManhattanDistance implements Distance {
    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        Double sumatorio = 0.0;
        for (int i = 0; i < p.size(); i++) {
            Double resta = (p.get(i) - q.get(i));
            sumatorio += resta;
        }
        return sumatorio;
    }
}
