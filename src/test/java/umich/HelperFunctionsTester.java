package umich;

import org.junit.Assert;
import org.junit.Test;

import umich.utils.HelperFunctions;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HelperFunctionsTester {

    private static HelperFunctions hf = new HelperFunctions();

    @Test
    public void testHelperFunctions() {

        double[] dose_times_1 = hf.linspace(0, 120 * 60, 1);
        System.out.println("Number of dose times: " + dose_times_1.length
        + "\n" + Arrays.stream(dose_times_1).mapToObj(Double::toString)
                .collect(Collectors.joining(", ", "[", "]")));
        Assert.assertEquals("Should have one dose time", 1, dose_times_1.length);

        double[] dose_times_20 = hf.linspace(0, 120 * 60, 20);
        System.out.println("Number of dose times: " + dose_times_20.length
                + "\n" + Arrays.stream(dose_times_20).mapToObj(Double::toString)
                .collect(Collectors.joining(", ", "[", "]")));
        Assert.assertEquals("Should have one dose time", 20, dose_times_20.length);


    }
}
