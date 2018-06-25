package umich.rates;

/**
 * Created by arjang on 5/27/17.
 */
public class RatesBuilder {

    private double _absorption, _secretion;
    private double _alpha, _beta, _gamma;
    private double _c, _tau, _s, _p1, _p2, _p3, _phi;

    public RatesBuilder() {
    }

    /**
     *
     * @return
     */
    public Rates buildSetRates() {
        return new Rates(_absorption, _secretion,
                _alpha, _beta, _gamma, _tau, _phi,
                _c, _s, _p1, _p2, _p3);
    }

    public RatesBuilder Absorption(Double absorption) { if(absorption!=null){this._absorption = absorption;} return this; }
    public RatesBuilder Secretion(Double secretion) { if(secretion!=null){this._secretion = secretion;} return this; }

    public RatesBuilder Alpha(Double alpha) { if(alpha!=null){this._alpha = alpha;} return this; }
    public RatesBuilder Beta(Double beta) { if(beta!=null){this._beta = beta;} return this; }
    public RatesBuilder Gamma(Double gamma) { if(gamma!=null){this._gamma = gamma;} return this; }
    public RatesBuilder Tau(Double tau) { if(tau!=null){this._tau = tau;} return this; }
    public RatesBuilder Phi(Double phi) { if(phi!=null){this._phi = phi;} return this; }

    public RatesBuilder C(Double c) { if(c!=null){this._c = c;} return this; }
    public RatesBuilder S(Double s) { if(s!=null){this._s = s;} return this; }

    public RatesBuilder P1(Double p1) { if(p1!=null){this._p1 = p1;} return this; }
    public RatesBuilder P2(Double p2) { if(p2!=null){this._p2 = p2;} return this; }
    public RatesBuilder P3(Double p3) { if(p3!=null){this._p3 = p3;} return this; }

}
