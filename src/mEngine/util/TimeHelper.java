package mEngine.util;

public class TimeHelper {

    public static boolean oneSecondPassed = false;
    public static float deltaTime;
    public static int FPS;
    public static int highestFPS;

    private static float lastTime = System.nanoTime() / 1000000;
    private static float lastFPS;
    private static long currentFPS;

    public static void setupTiming() {

        lastTime = getTime();
        lastFPS = getTime();

    }

    public static float getTime() {
        return System.nanoTime() / 1000000;
    }

    public static void updateDeltaTime() {

        float thisTime = getTime();
        deltaTime = thisTime - lastTime;
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

}
