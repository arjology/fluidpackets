package umich;

import java.util.Random;

import umich.rates.Rates;
import umich.rates.RatesBuilder;
/**
 * Created by arjang on 5/27/17.
 */

public class Compartment implements Volume {

    private String compartmentName;
    private String compartmentType;
    private double compartmentVol;
    private double meanPacketVol ;

    private double absorptionRate;
    private double transit;
    private double secretionRate;

    private double alpha;
    private double beta;
    private double gamma;

    private Random random = new Random();

    /**
     *
     *
     */
    private Rates rates = new RatesBuilder()
            .Absorption(absorptionRate).Secretion(secretionRate)
            .Alpha(alpha).Beta(beta).Gamma(gamma)
            .buildSetRates();

    /**
     *
     * @param rates
     */
    public void setRates(Rates rates) {
        this.rates = rates;
    }

    /**
     *
     * @param compartmentName
     * @param compartmentVol
     * @param packetVol
     */
    public Compartment(String compartmentName, String compartmentType, double compartmentVol, double packetVol) {
        this.compartmentName = compartmentName;
        this.compartmentType = compartmentType;
        this.compartmentVol = compartmentVol;
        this.meanPacketVol = packetVol;
    }

    /**
     *
     * @return
     */
    public double generatePacket() {
        double packetVariability = random.nextGaussian()*2/(1+Math.exp(-1.5*(compartmentVol-10)));
        double Z = random.nextGaussian()*packetVariability;
        return ((Z + meanPacketVol)/(1+rates.getBeta()*Math.exp(-(rates.getGamma()/(10.0*rates.getGamma()))*(compartmentVol + (Z+meanPacketVol)))));
    }

    public double nextTime(double lambda) {return Math.log(1-random.nextDouble())/(-lambda); }
    public void inFlow(double volume) {compartmentVol += volume;}
    public void outFlow(double volume) {compartmentVol -= volume;}
    public void secrete(double dT) {compartmentVol += + dT*rates.getSecretion();}
    public void absorb(double dT) {compartmentVol -= - dT*rates.getAbsorption()/(1 + 2000*Math.exp(-.75*(this.compartmentVol - 2)/2));}
    public double getVolume() { return compartmentVol; }
    public void setVolume(double vol) { compartmentVol = vol; }
    public String toString() {return compartmentName;}
    public String getRates() { return this.rates.toString(); }

}
