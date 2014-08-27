package com.mGameLabs.mEngine.util.data;

public class ArrayHelper {

    public static double[] fillArray(int arrayLength) {
        return fillArray(arrayLength, 0);
    }

    public static double[] fillArray(int arrayLength, int placeholder) {

        double[] output = new double[arrayLength];
        for (int i = 0; i < arrayLength; i++) output[i] = placeholder;
        return output;

    }

}
