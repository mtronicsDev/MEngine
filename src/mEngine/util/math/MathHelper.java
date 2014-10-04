/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.math;

public class MathHelper {

    public static double clamp(double input, double min, double max) {
        if (input < min) input = min;
        if (input > max) input = max;
        return input;
    }

    public static double clampMin(double input, double min) {
        if (input < min) return min;
        else return input;
    }

    public static boolean isEven(int input) {
        return input % 2 == 0;
    }

}
