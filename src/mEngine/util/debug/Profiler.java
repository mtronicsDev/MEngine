package mEngine.util.debug;

import mEngine.util.TimeHelper;
import mEngine.util.math.graphs.Graph;

import static mEngine.util.RuntimeHelper.*;

public class Profiler {

    private static Graph memUsage; //Used for saving values of getMemoryGraph()
    private static Graph fpsGraph; //Used for saving values of getFPSGraph()

    public static Graph getMemoryGraph(int valueCount) {

        if(memUsage == null) memUsage = new Graph(valueCount);
        for(int i = 1; i < memUsage.getLength(); i++) {

            if(i - 1 >= 0) memUsage.updateValue(i - 1, memUsage.getX(i));

        }

        memUsage.updateValue(memUsage.getLength() - 1, (float)getMemoryStats(USED_MEMORY) / getMemoryStats(TOTAL_MEMORY) * 100);

        return memUsage;

    }

    public static Graph getFPSGraph(int valueCount) {

        if(fpsGraph == null) fpsGraph = new Graph(valueCount);
        for(int i = 1; i < fpsGraph.getLength(); i++) {

            if(i - 1 >= 0) fpsGraph.updateValue(i - 1, fpsGraph.getX(i));

        }

        fpsGraph.updateValue(fpsGraph.getLength() - 1, (float)TimeHelper.FPS / TimeHelper.highestFPS * 100); //Show fps in dependency of the highest fps reached in the current run

        return fpsGraph;

    }

}
