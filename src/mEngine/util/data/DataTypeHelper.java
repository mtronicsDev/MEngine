package mEngine.util.data;

public class DataTypeHelper {

    public static Double[] doublePrimitiveToObject(double[] input) {

        Double[] output = new Double[input.length];

        for (int i = 0; i < input.length; i++) {

            output[i] = input[i];

        }

        return output;

    }

}
