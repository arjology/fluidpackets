package umich.rates;

/**
 * Created by arjang on 5/27/17.
 */
public class Rates {

    private double _absorption, _secretion;
    private double _alpha, _beta, _gamma;
    private double _c, _tau, _s, _p1, _p2, _p3, _phi;

    /**
     *
     * @param absorption
     * @param secretion
     * @param alpha
     * @param beta
     * @param gamma
     * @param tau
     * @param phi
     * @param c
     * @param s
     * @param p1
     * @param p2
     * @param p3
     */
    public Rates(double absorption, double secretion,
                 double alpha, double beta, double gamma, double tau, double phi,
                 double c, double s, double p1, double p2, double p3) {

        this._absorption = absorption;
        this._secretion = secretion;

        this._alpha = alpha;
        this._beta = beta;
        this._gamma = gamma;

        this._c = c;
        this._tau = tau;

        this._s = s;

        this._p1 = p1;
        this._p2 = p2;
        this._p3 = p3;
        this._phi = phi;

    }

    public Rates() {
    }

    public double getAbsorption() { return _absorption; }
    public double getSecretion() { return _secretion; }

    public double getAlpha() { return _alpha; }
    public double getBeta() { return _beta; }
    public double getGamma() { return  _gamma; }
    public double getTau() { return _tau; }
    public double getPhi() { return _phi; }

    public double getC() { return _c; }
    public double getS() { return _s; }

    public double getP1() { return  _p1; }
    public double getP2() { return _p2; }
    public double getP3() { return _p3; }

    public void setAbsorption(double absorption) { this._absorption = absorption ; }
    public void setSecretion(double secretion) { this._secretion = secretion; }

    public void setAlpha(double alpha) { this._alpha = alpha; }
    public void setBeta(double beta) { this._beta = beta; }
    public void setGamma(double gamma) { this._gamma = gamma; }
    public void setTau(double tau) { this._tau = tau; }
    public void setPhi(double phi) { this._phi = phi; }


    public void setC(double c) { this._c = c; }
    public void setS(double s) { this._s = s; }

    public void setP1(double p1) { this._p1 = p1; }
    public void setP2(double p2) { this._p2 = p2; }
    public void setP3(double p3) { this._p3 = p3; }

    public String toString() {
        String s = "";
        return "\n\tAbsorption: " + _absorption +
                "\n\tSecretion: " + _secretion +
                "\n\tAlpha: " + _alpha +
                "\n\tBeta: " + _beta +
                "\n\tGamma: " + _gamma +
                "\n\tTau: :" + _tau +
                "\n\tPhi: " + _phi +
                "\n\tC: " + _c +
                "\n\tS: " + _s +
                "\n\tp1: " + _p1 +
                "\n\tp2: " + _p2 +
                "\n\tp3: " + _p3;
    }

}
