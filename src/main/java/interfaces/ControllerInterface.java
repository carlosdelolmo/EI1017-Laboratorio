package interfaces;

import mvc.Model;
import mvc.View;

import java.io.FileNotFoundException;

public interface ControllerInterface {
    void setView(View view);
    void setModel(Model model);
    void loadData();
    void estimateParams(String puntoAEstimar);
}
