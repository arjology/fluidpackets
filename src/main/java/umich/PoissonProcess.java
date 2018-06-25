package umich;

import java.io.File;
import java.util.stream.IntStream;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import umich.rates.Rates;
import umich.rates.RatesBuilder;

/**
 * Created by arjang on 5/28/17.
 */
public class PoissonProcess {

    private Config config = ConfigFactory.parseFile(new File("gi.conf"));

    private double tau = config.getDouble("poisson.tau"); //59*60.0;
    private double phi = config.getDouble("poisson.phi"); //1.0/3600.0;

    private double c = config.getDouble("poisson.c"); //1.5;
    private double s = config.getDouble("poisson.s"); //0.01;

    private double p1 = config.getDouble("poisson.p1"); //0.11;
    private double p2 = config.getDouble("poisson.p2"); //8.0*Math.pow(10,-7);
    private double p3 = config.getDouble("poisson.p3"); //8.4;

    /**
     *
     */
    private Rates rates = new RatesBuilder()
            .C(c).S(s)
            .Phi(phi).Tau(tau)
            .P1(p1).P2(p2).P3(p3)
            .buildSetRates();

    /**
     *
     * @param _rates
     */
    public void setRates(Rates _rates) {
        this.rates = _rates;
    }

    public PoissonProcess() {
        setRates(this.rates);
    }

    /**
     *
     * @param volume
     * @param t
     * @return double
     */
    public double lambda(double volume, double t) {
        double time = t % (1 / (rates.getPhi() / 2)) - rates.getTau();
        double volScaling = ((rates.getC() / 2) / (1 + 35 * Math.exp(-.5 * (volume - 10.0) / 10.0)));
        double sum = IntStream.range(1,26).mapToDouble(k -> Math.pow(-1,k)*Math.sin(-rates.getPhi()*Math.PI*k*time)/k + rates.getP1()).sum();
        return volScaling * (rates.getP2() * Math.pow(sum,rates.getP3()) + rates.getS());
    }

    /**
     *
     * @param vol
     * @param t
     * @return double
     */
    public double lambdaSI(double vol, double t) {
        double time = t % (1 / (rates.getPhi() / 2)) - rates.getTau();
        double volScaling = ((rates.getC())/(1 + 35*Math.exp(-.5*(150 - 10.0)/10.0)));
        double sum = IntStream.range(1,26).mapToDouble(k -> Math.pow(-1,k)*Math.sin(-rates.getPhi()*Math.PI*k*time)/k + rates.getP1()).sum();
        return volScaling*(rates.getP2()*Math.pow(sum,rates.getP3()*1.2) + rates.getS());
    }
}
