package mEngine.util.math.graphs;

public class GraphHelper {

    public Graph shiftValues(Graph graph, int offset) { //Shifts all the values contained in a graph by profiler specified amount of steps

        if (offset < 0) {

            for (int i = offset; i < graph.values.length; i++) {
                if (i - offset >= 0) graph.values[i - offset] = graph.values[i];
            }

        }

        if (offset > 0) {

            for (int i = graph.values.length - offset - 1; i >= 0; i++) {
                graph.values[i + offset] = graph.values[i];
            }

        }

        return graph;

    }

}
