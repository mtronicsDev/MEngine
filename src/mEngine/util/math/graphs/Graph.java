package mEngine.util.math.graphs;

import mEngine.util.ArrayHelper;

public class Graph {

    protected double[] values;

    public Graph(int valueCount) {
        values = ArrayHelper.fillArray(valueCount);
    }

    public Graph(double[] values) {
        this.values = values;
    }

    public double getY(int x) {
        return values[x];
    }

    public int getLength() {
        return values.length;
    }

    public void updateValue(int x, double y) {
        values[x] = y;
    }

    public double[] getValues() {
        return values;
    }

}