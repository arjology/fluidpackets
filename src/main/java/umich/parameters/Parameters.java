package umich.parameters;

public class Parameters {

    private Boolean _runModel, _runLambda = false;
    private String _output = null;
    private Double _dose = null;
    private Double _doseVol = null;
    private Integer _doseTimesCount = null;
    private Integer _iterations = null;

    public Parameters(boolean runModel, boolean runLambda,
                      String output, double dose, double dose_vol,
                      int dose_times_count, int iterations) {
        this._runModel = runModel;
        this._runLambda = runLambda;
        this._output = output;
        this._dose = dose;
        this._doseVol = dose_vol;
        this._doseTimesCount = dose_times_count;
        this._iterations = iterations;
    }

    public Parameters() {
    }

    public boolean getRunModel() { return _runModel; }
    public boolean getRunLambda() { return _runLambda; }
    public String getOutput() { return _output; }
    public double getDose() { return _dose; }
    public double getDoseVol() { return _doseVol; }
    public int getDoseTimesCount() { return _doseTimesCount; }
    public int getIterations() { return _iterations; }

    public void setRunModel(boolean runModel) { _runModel = runModel; }
    public void setRunLambda(boolean runLambda) { _runLambda = runLambda; }
    public void setOutput(String output) { _output = output; }
    public void setDose(double dose) { _dose = dose; }
    public void setDoseVol(double doseVol) { _doseVol = doseVol; }
    public void setDoseTimesCount(int doseTimesCount) { _doseTimesCount = doseTimesCount; }
    public void setIterations(int iterations) { _iterations = iterations; }

}
