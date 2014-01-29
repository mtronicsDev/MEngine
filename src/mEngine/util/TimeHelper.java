package mEngine.util;

public class TimeHelper {

    private static long lastTime = System.nanoTime() / 1000000;

    public static float deltaTime;
    public static int FPS;

    private static long lastFPS;
    private static long currentFPS;

    public static void setupTiming() {

        lastTime = getTime();
        lastFPS = getTime();

    }

    public static long getTime() { return System.nanoTime() / 1000000; }

    public static void updateDeltaTime() {

        long thisTime = getTime();
        deltaTime = (thisTime - lastTime) / 1000f;
        lastTime = thisTime;

    }

    public static void updateFPS() {

        if(getTime() - lastFPS > 1000) {

            FPS = (int)currentFPS;
            currentFPS = 0;
            lastFPS += 1000;

        }

        currentFPS++;

    }

}
