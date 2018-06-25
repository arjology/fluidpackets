package umich.parameters;

public class ParameterBuilder {

    private Boolean _runModel, _runLambda = false;
    private String _output = null;
    private Double _dose = null;
    private Double _doseVol = null;
    private Integer _doseTimesCount = null;
    private Integer _iterations = null;

    public ParameterBuilder() {
    }

    public Parameters buildSetParameters() {
        return new Parameters(_runModel, _runLambda, _output,
                _dose, _doseVol, _doseTimesCount, _iterations);
    }

    public ParameterBuilder RunModel(Boolean runModel) { if(runModel!=null){this._runModel = runModel;} return this; }
    public ParameterBuilder RunLambda(Boolean runLambda) { if(runLambda!=null){this._runLambda = runLambda;} return this; }
    public ParameterBuilder Output(String output) { if(output!=null){this._output = output;} return this; }
    public ParameterBuilder Dose(Double dose) { if(dose!=null){this._dose = dose;} return this; }
    public ParameterBuilder DoseVol(Double dose_vol) { if(dose_vol!=null){this._doseVol = dose_vol;} return this; }
    public ParameterBuilder DoseTimesCount(Integer dose_times_count) { if(dose_times_count!=null){this._doseTimesCount = dose_times_count;} return this; }
    public ParameterBuilder Iterations(Integer iterations) { if(iterations!=null){this._iterations = iterations;} return this; }

}
