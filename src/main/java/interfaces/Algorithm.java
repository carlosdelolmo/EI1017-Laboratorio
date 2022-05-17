package interfaces;

import table.Table;

public interface Algorithm <T extends Table, E, R>{
    void train(T datos);
    R estimate(E valores);

}
