/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.data;

public class DataTypeHelper {

    /**
     * Converts a boolean into an integer.
     * This is necessary for some OpenGL interactions
     *
     * @param b The boolean to be converted
     * @return The integer output
     */
    public static int booleanToInteger(boolean b) {
        if (b) return 1;
        else return 0;
    }

}
