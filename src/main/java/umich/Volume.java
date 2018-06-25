package umich;

/**
 * Created by arjang on 5/27/17.
 */
public interface Volume {

    public double nextTime(double lambda);
    public void inFlow(double vol);
    public void outFlow(double vol);
    public void secrete(double dT);
    public void absorb(double dT);
    public double getVolume();
    public void setVolume(double vol);
    public String toString();

}
