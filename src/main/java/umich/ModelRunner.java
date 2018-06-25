package umich;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import umich.utils.Pairs2D;
import umich.utils.HelperFunctions;
import umich.parameters.Parameters;

public class ModelRunner {

    private static Logger log = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    private static HelperFunctions hf = new HelperFunctions();

    private static void runTransitModel(Parameters params) throws Exception {

        log.info("*** Running GI Transit Model.");

        String anim = "|/-\\";

        int totalRunTime = 28800;
        int N = totalRunTime;
        int startTime = 0;
        double Dt = (totalRunTime - startTime) / (float) N;
        double initialVolume = 35.0 + params.getDoseVol();
        double[] dose_times = hf.linspace(0, 120 * 60, params.getDoseTimesCount());

        int totalIterations = params.getIterations() * dose_times.length;
        double[][][] resultVols = new double[totalIterations][totalRunTime][2];
        double[][][] resultPackets = new double[totalIterations][totalRunTime][2];
        double[][] X1 = null;
        double[][] X2 = null;

        double pct_complete;

        log.info("*** Total iterations: " + totalIterations);

        log.info("*** StomachTransit parameters:" +
                "\n\tStarting time:\t" + startTime +
                "\n\tTotal runtime:\t" + totalRunTime +
                "\n\tInitial volume:\t" + initialVolume +
                "\n\tDosing times:\t" + dose_times.length);

        GITransitBuilder GITransit =
                new GITransitBuilder(startTime, totalRunTime, Dt, initialVolume, dose_times[0]);

        log.info("*** Stomach rates: " + GITransit.stomach.getRates());
        log.info("*** Dose times: " +
                Arrays.stream(dose_times).mapToObj(Double::toString)
                .collect(Collectors.joining(", ", "[", "]")));

        for (int i = 0; i < dose_times.length; i++) {
            GITransit.setDoseTime(dose_times[i]);
            System.out.println("*** Dose time: " + GITransit.getDoseTime());

            for (int k = 0; k < params.getIterations(); k++) {
                int idx = params.getIterations() * i + k + 1;
                pct_complete = Math.round((float) (idx)/totalIterations*100*10.0)/10.0;

                String data = "\r" + "--- Calculating Results " +
                        anim.charAt(idx % anim.length()) + " "  + idx + "/" + totalIterations + " [" + pct_complete + "%]" ;
                System.out.write(data.getBytes());

                GITransit.transit();
                Pairs2D rs = GITransit.getResults();

                resultVols[idx-1] = rs.getArray1();
                if (X1==null) { X1 = resultVols[idx-1]; }
                else { X1 = hf.append(X1,resultVols[idx-1]); }

                resultPackets[idx-1] = rs.getArray2();
                if (X2==null) { X2 = resultPackets[idx-1]; }
                else { X2 = hf.append(X2,resultPackets[idx-1]); }
            }
        }

        params.setOutput((params.getOutput() == null || params.getOutput().isEmpty()) ? "results_" : params.getOutput());
        hf.saveResults(X1, params.getOutput() + "vols.txt");
        hf.saveResults(X2, params.getOutput() + "packets.txt");
    }

    private static void runLambdaModel(Parameters params) throws Exception {

        log.info("*** Analyzing lambda function of Poisson process.");

        PoissonProcess pProc = new PoissonProcess();

        int TT = 9000;
        int t0 = 0;

        double[] t = hf.linspace(t0,TT,TT+1);
        double[] lmbd = IntStream.range(0,t.length).mapToDouble(i -> pProc.lambda(200,t[i])).toArray();
        hf.saveResults(lmbd, "lmbd.txt");
    }

    public static void main(String[] args) throws Exception {

        Date date = new Date();
        String start_time = date.toString();
        log.info("*** " + start_time);

        Parameters params = hf.setParameters(args);
        if(params.getRunModel()) { runTransitModel(params); }
        else if(params.getRunLambda()){ runLambdaModel(params); }


    }

}
