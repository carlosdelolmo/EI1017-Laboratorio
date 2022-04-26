package interfaces;

import mvc.View;

import java.util.List;

public interface ModelInterface {
    void registerView(View obs);
    int getNumRows();
    void notifyViews();

    List<Double> getData(int i);
}
