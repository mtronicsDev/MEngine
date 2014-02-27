package mEngine.util;

public class GraphHelper {

    public double[] shiftValues(double[] values, int offset) {

        if(offset < 0) {

            for(int i = offset; i < values.length; i++) { if(i - offset >= 0) values[i - offset] = values[i]; }

        }

        if(offset > 0) {

            for(int i = values.length - offset - 1; i >= 0; i++) { values[i + offset] = values[i]; }

        }

        return values;

    }

}
