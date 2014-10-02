/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.math;

import java.text.DecimalFormat;

public class NumberFormatHelper {

    public static String format(double input, String format) {

        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(input);

    }

    public static String cutDecimals(double input, int decimals) {

        String format = "0.";
        if (decimals == 0) format = "0";
        else for (int i = 0; i < decimals; i++) {
            format = format + "0";
        }

        return format(input, format);

    }

}
