package mEngine.util;

import mEngine.graphics.GraphicsController;
import org.lwjgl.Sys;

import static mEngine.util.RuntimeHelper.*;

public class TimeHelper {

    private static long lastTime = System.currentTimeMillis();
    private static long thisTime;
    public static float deltaTime;
    private static long lastFPS;

    public static long currentFPS;

    public static void setupTiming() {

        lastTime = getTime();
        lastFPS = getTime();

    }

    public static long getTime() { return System.currentTimeMillis(); }

    public static void updateDeltaTime() {

        thisTime = getTime();
        deltaTime = (thisTime - lastTime) / 1000f;
        lastTime = thisTime;

    }

    public static void updateFPS() {

        if(getTime() - lastFPS > 1000) {

            GraphicsController.setWindowTitle( "mEngine Test Run @ " + currentFPS + " FPS | MemUsage: [Total: " + getMemoryStats(TOTAL_MEMORY) +
                    " MB | Used: " + getMemoryStats(USED_MEMORY) +
                    " MB | Free: " + getMemoryStats(FREE_MEMORY) + " MB]");
            currentFPS = 0;
            lastFPS += 1000;

        }

        currentFPS++;

    }

}
