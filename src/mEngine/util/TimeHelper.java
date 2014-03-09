package mEngine.util;

import mEngine.util.math.graphs.Graph;

public class TimeHelper {

    public static boolean oneSecondPassed = false;
    public static float deltaTime;
    public static int FPS;
    public static int highestFPS;

    private static float lastTime = System.nanoTime() / 1000000;
    private static float lastFPS;
    private static long currentFPS;

    private static Graph fpsGraph;


    public static void setupTiming() {

        lastTime = getTime();
        lastFPS = getTime();

    }

    public static float getTime() { return System.nanoTime() / 1000000; }

    public static void updateDeltaTime() {

        float thisTime = getTime();
        deltaTime = thisTime - lastTime;
        lastTime = thisTime;

    }

    public static void updateFPS() {

        oneSecondPassed = false;
        if(getTime() - lastFPS > 1000) {

            oneSecondPassed = true;
            FPS = (int)currentFPS;
            if(currentFPS > highestFPS) highestFPS = FPS;
            currentFPS = 0;
            lastFPS += 1000;

        }

        currentFPS++;

    }

    public static Graph getFPSGraph(int valueCount) {

        if(fpsGraph == null) fpsGraph = new Graph(valueCount);
        for(int i = 1; i < fpsGraph.getLength(); i++) {

            if(i - 1 >= 0) fpsGraph.updateValue(i - 1, fpsGraph.getX(i));

        }

        fpsGraph.updateValue(fpsGraph.getLength() - 1, (float)FPS / highestFPS * 100); //Show fps in dependency of the highest fps reached in the current run

        return fpsGraph;

    }

}
