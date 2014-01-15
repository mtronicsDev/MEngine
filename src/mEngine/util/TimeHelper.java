package mEngine.util;

import mEngine.graphics.GraphicsController;
import org.lwjgl.Sys;

public class TimeHelper {

    private static long lastTime;
    private static long lastFPS;

    public static long currentFPS;

    public static void setupTiming() {

        lastTime = getTime();
        lastFPS = getTime();

    }

    public static long getTime() { return (Sys.getTime() * 1000) / Sys.getTimerResolution(); }

    public static int deltaTime() {

        long time = getTime();
        int deltaTime = (int)(time - lastTime);
        lastTime = time;

        return deltaTime;

    }

    public static void updateFPS() {

        if(getTime() - lastFPS > 1000) {

            GraphicsController.setWindowTitle( "mEngine Test Run @ " + currentFPS + " FPS");
            currentFPS = 0;
            lastFPS += 1000;

        }

        currentFPS++;

    }

}
