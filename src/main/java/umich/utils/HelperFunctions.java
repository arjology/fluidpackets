package umich.utils;

import org.apache.commons.cli.*;
import umich.parameters.ParameterBuilder;
import umich.parameters.Parameters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HelperFunctions {

    public double[] linspace(double min, double max, int points) {
        double[] myArray = new double[points];
        if (points==1){myArray[0] = 0.0;}
        else {myArray = IntStream.range(0, points).mapToDouble(i -> min + i * (max - min) / (points - 1)).toArray(); }
        return myArray;
    }

    public void saveResults(double[][] X, String title) throws Exception {

        System.out.println("\n--- Writing results to file: " + title);
        FileWriter fw = new FileWriter(new File(title));
        BufferedWriter bw = new BufferedWriter (fw);
        String csv = "";
        try {
            csv = Arrays.stream(X)
                    .map(row -> Arrays.stream(row).mapToObj(Double::toString)
                            .collect(Collectors.joining(", ")))
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bw.write(csv);

//        FileWriter fw;
//        try {
//            fw = new FileWriter(new File(title));
//            BufferedWriter bw = new BufferedWriter (fw);
//            for (int i = 0; i < X.length; i=i+60) {
//                for (int j = 0; j < X[i].length; j++) {
//                    bw.write(String.valueOf(X[i][j]) + ",");
//                }
//                bw.newLine();
//            }
//            bw.close();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
    }

    public void saveResults(double[] X, String title) throws Exception {
        System.out.println("\n--- Writing results to file: " + title);
        FileWriter fw;
        try {
            fw = new FileWriter(new File("lmbda.txt"));
            BufferedWriter bw = new BufferedWriter (fw);
            for (int j = 0; j < X.length; j++) {
                bw.write(String.valueOf(X[j]) + ",");
            }
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public double[][] append(double[][] a, double[][] b) {
        double[][] result = new double[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public double[] append(double[] a, double[] b) {
        double[] result = new double[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public Parameters setParameters(String[] args) throws Exception{

        ParameterBuilder paramsBuilder = new ParameterBuilder();
        Options options = new Options();

        Option outputLambda = new Option("l", "lambda", true, "Analyze lambda function of Poisson process.");
        Option outputModel = new Option("r", "run-transit", true, "Run transit model.");
        Option inputDose = new Option("d", "dose", true, "Initial dose.");
        Option inputDoseVol = new Option("v", "volume", true, "Fluid volume dosed.");
        Option inputDoseTime = new Option("t", "time", true, "Number of dose-time points to compute.");
        Option outputFile = new Option("f", "filename", true, "Filename to save results.");
        Option inputIterations = new Option("i", "iterations", true, "Number of iterations to simulate per dose time.");

        outputModel.setRequired(false);
        outputModel.setType(Boolean.class);
        options.addOption(outputModel);

        outputLambda.setRequired(false);
        outputLambda.setType(Boolean.class);
        options.addOption(outputLambda);

        inputDose.setRequired(true);
        inputDose.setType(Number.class);
        options.addOption(inputDose);

        inputDoseVol.setRequired(true);
        inputDoseVol.setType(Number.class);
        options.addOption(inputDoseVol);

        inputDoseTime.setRequired(true);
        inputDoseVol.setType(Number.class);
        options.addOption(inputDoseTime);

        inputIterations.setRequired(true);
        inputIterations.setType(Number.class);
        options.addOption(inputIterations);

        outputFile.setRequired(false);
        options.addOption(outputFile);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        cmd = parser.parse(options, args);

        Boolean runModel = false;
        Boolean runLambda = false;
        String output = null;
        Double dose = null;
        Double doseVol = null;
        Integer doseTimesCount = null;
        Integer iterations = null;

        runModel = Boolean.parseBoolean(cmd.getOptionValue("r"));
        runLambda = Boolean.parseBoolean(cmd.getOptionValue("l"));
        output = cmd.getOptionValue("f");
        dose = Double.parseDouble(cmd.getOptionValue("d"));
        doseVol = Double.parseDouble(cmd.getOptionValue("v"));
        doseTimesCount = Integer.parseInt(cmd.getOptionValue("t"));
        iterations = Integer.parseInt(cmd.getOptionValue("i"));

        return paramsBuilder
                .RunModel(runModel)
                .RunLambda(runLambda)
                .Output(output)
                .Dose(dose)
                .DoseVol(doseVol)
                .DoseTimesCount(doseTimesCount)
                .Iterations(iterations)
                .buildSetParameters();
    }

}
