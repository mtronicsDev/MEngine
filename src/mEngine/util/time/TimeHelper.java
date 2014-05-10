package mEngine.util.time;

public class TimeHelper {

    public static boolean oneSecondPassed = false;
    public static float deltaTime;
    public static int FPS;
    public static int highestFPS;
    public static int TPS;
    public static int highestTPS;
    public static float timeModifier = 1;
    private static float lastTime = System.nanoTime() / 1000000;
    private static float lastFPS;
    private static long currentFPS;
    private static float lastTPS;
    private static long currentTPS;

    public static void setupTiming() {

        lastTime = getTime();
        lastFPS = getTime();
        lastTPS = getTime();

    }

    public static long getTime() {
        return System.nanoTime() / 1000000;
    }

    public static void updateDeltaTime() {

        float thisTime = getTime();
        deltaTime = thisTime - lastTime;
        deltaTime *= timeModifier;
        lastTime = thisTime;

    }

    public static void updateFPS() {

        oneSecondPassed = false;
        if (getTime() - lastFPS > 1000) {

            oneSecondPassed = true;
            FPS = (int) currentFPS;
            if (currentFPS > highestFPS) highestFPS = FPS;
            currentFPS = 0;
            lastFPS += 1000;

        }

        currentFPS++;

    }

    public static void updateTPS() {

        if (getTime() - lastTPS > 1000) {

            TPS = (int) currentTPS;
            if (currentTPS > highestTPS) highestTPS = TPS;
            currentTPS = 0;
            lastTPS += 1000;

        }

        currentTPS++;

    }

    public static void timeOut(long milliseconds) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void updateTimeModifier() {

        if (timeModifier == 1f / 8f) timeModifier = 1f;

        else if (timeModifier == 1) timeModifier = 8f;

        else if (timeModifier == 8) timeModifier = 1f / 8f;

    }

}
