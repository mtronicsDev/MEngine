package mEngine.util.math;

public class MathHelper {

    public static double clamp(double input, double min, double max) {

        if(input < min) input = min;
        if(input > max) input = max;
        return input;

    }

    public static double[] fillArray(int arrayLength) { return fillArray(arrayLength, 0); }

    public static double[] fillArray(int arrayLength, int placeholder) {

        double[] output = new double[arrayLength];
        for(int i = 0; i < arrayLength; i++) output[i] = placeholder;
        return output;

    }

}
