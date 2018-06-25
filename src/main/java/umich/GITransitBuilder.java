package umich;

import umich.utils.Pairs;
import umich.utils.Pairs2D;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;

import umich.rates.Rates;
import umich.rates.RatesBuilder;
import umich.utils.HelperFunctions;

/**
 * Created by arjang on 5/29/17.
 */
public class GITransitBuilder implements TransitBuilder {

    private Integer totalRunTime;
    private Double Dt, timeOfDose;
    private double[] t;
    private double[][] volumeTrackerList;
    private double[][] packetTrackerList;

    private PoissonProcess pProc;
    public Compartment stomach;

    /**
     * @param startTime
     * @param totalRunTime
     * @param Dt
     * @param initialVolume
     * @param timeOfDose
     */
    public GITransitBuilder(int startTime, int totalRunTime, double Dt, double initialVolume, double timeOfDose) {

        RatesBuilder stomachRates = new RatesBuilder();
        String compartmentName = "stomach";
        String compartmentType = "stomach";

        Config config = ConfigFactory.parseFile(new File("gi.conf"));

        double absRate = config.getDouble(compartmentType + ".absorptionRate");
        double secRate = config.getDouble(compartmentType + ".secretionRate");
        double alpha = config.getDouble( "poisson.alpha");
        double beta = config.getDouble("poisson.beta");
        double gamma = config.getDouble("poisson.gamma");
        double tau = config.getDouble("poisson.tau");
        double phi = config.getDouble("poisson.phi");
        double c = config.getDouble("poisson.c");
        double s = config.getDouble("poisson.s");
        double p1 = config.getDouble("poisson.p1");
        double p2 = config.getDouble("poisson.p2");
        double p3 = config.getDouble("poisson.p3");

        double averagePacketVolume = config.getDouble(compartmentType + ".averagePacketVolume");

        this.totalRunTime = totalRunTime;
        this.Dt = Dt;
        this.timeOfDose = timeOfDose;

        this.volumeTrackerList = new double[totalRunTime][2];
        this.volumeTrackerList[0][0] = 0.0d;
        this.volumeTrackerList[0][1] = initialVolume;

        this.packetTrackerList = new double[totalRunTime][2];
        this.packetTrackerList[0][0] = 0.0d;
        this.packetTrackerList[0][1] = 0.0d;

        HelperFunctions hf = new HelperFunctions();
        t = hf.linspace(startTime, totalRunTime, totalRunTime + 1);

        Rates rates = stomachRates
                .Absorption(absRate)
                .Secretion(secRate)
                .Alpha(alpha)
                .Beta(beta)
                .Gamma(gamma)
                .Tau(tau)
                .Phi(phi)
                .C(c)
                .S(s)
                .P1(p1)
                .P2(p2)
                .P3(p3)
                .buildSetRates();
        this.pProc = new PoissonProcess();
        this.pProc.setRates(rates);
        this.stomach = new Compartment(compartmentName, compartmentType, initialVolume, averagePacketVolume);
        this.stomach.setRates(rates);
    }

    public void transit() {

        double t_s = 0;
        double t_sNext;
        double lambdaStomach;
        double pktFwd;

        for (int i = 1; i < totalRunTime; i++) {

            System.out.println("\n\n\n+++ Time: " + t[i] + "\tDose time: " + timeOfDose);
            lambdaStomach = pProc.lambda(stomach.getVolume(), t[i] + timeOfDose);
            t_sNext = stomach.nextTime(lambdaStomach);
            System.out.println("+++ Lambda: " + lambdaStomach + "\tCurrent time: " + t_s + "\tNext time: " + t_sNext + "\tIndex time: " + t[i]);
            t_s = (t_s + t_sNext < t[i]) ? t[i] : t_s;

            pktFwd = stomach.generatePacket();
            System.out.println("+++ Stomach volume: " + stomach.getVolume() + "\tGenerated packet: " + pktFwd);
            System.out.println("+++ Transitable: " + (Math.abs(t[i]-(t_s+t_sNext))<=Dt));
            pktFwd = (Math.abs(t[i] - (t_s + t_sNext)) <= Dt) ? ((stomach.getVolume() - pktFwd > 0) ? pktFwd : 0) : 0;
            System.out.println("+++ Sufficient volume to empty packet: " + (stomach.getVolume() - pktFwd > 0));

            System.out.println("+++ Stomach volume: " + stomach.getVolume());
            stomach.secrete(Dt);
            System.out.println("+++ Stomach volume after secretion: " + stomach.getVolume());
            stomach.outFlow(pktFwd);
            System.out.println("+++ Stomach volume after outflow: " + stomach.getVolume());


            packetTrackerList[i][1] = pktFwd;
            packetTrackerList[i][0] = t[i];

            volumeTrackerList[i][1] = stomach.getVolume();
            volumeTrackerList[i][0] = t[i];

        }
    }
    public Pairs2D getResults() { return new Pairs2D(volumeTrackerList, packetTrackerList); }
    public void setDoseTime(double _doseTime) { this.timeOfDose = _doseTime; }
    public double getDoseTime() { return timeOfDose; }
}
