package umich;

import umich.utils.Pairs2D;

import java.io.Serializable;

/**
 * Created by arjang on 5/29/17.
 */
public interface TransitBuilder extends Serializable {
    void transit();
    Pairs2D getResults();
    void setDoseTime(double _doseTime);
    double getDoseTime();
}
