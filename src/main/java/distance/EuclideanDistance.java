package distance;

import interfaces.Distance;

import java.util.List;

public class EuclideanDistance implements Distance {
    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        double sumatorio = 0.0;
        for (int i = 0; i < p.size(); i++) {
            double resta = (p.get(i) - q.get(i));
            resta = resta * resta;
            sumatorio += resta;
        }
        return Math.sqrt(sumatorio);
    }
}
