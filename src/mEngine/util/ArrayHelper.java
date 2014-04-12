package mEngine.util;

public class ArrayHelper {

    public static double[] fillArray(int arrayLength) {
        return fillArray(arrayLength, 0);
    }

    public static double[] fillArray(int arrayLength, int placeholder) {

        double[] output = new double[arrayLength];
        for (int i = 0; i < arrayLength; i++) output[i] = placeholder;
        return output;

    }

    public static boolean[] fillArray(int arrayLength, boolean placeHolder) {

        boolean[] output = new boolean[arrayLength];
        for (int count = 0; count < output.length; count++) output[count] = placeHolder;
        return output;

    }

}
