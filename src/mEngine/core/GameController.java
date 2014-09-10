package mEngine.core;

import mEngine.audio.AudioController;
import mEngine.graphics.renderable.LoadingScreen;
import mEngine.util.debug.RuntimeHelper;
import mEngine.util.resources.PreferenceHelper;
import mEngine.util.resources.ResourceHelper;
import mEngine.util.threading.ThreadHelper;
import mEngine.util.time.TimeHelper;
import org.lwjgl.input.Mouse;

import static mEngine.core.ObjectController.*;

public class GameController {

    public static boolean isGamePaused;
    public static boolean isLoading;

    public static void runGame() {

        ResourceHelper.initialize();
        PreferenceHelper.loadPreferences("mEngine");
        AudioController.initialize();
        TimeHelper.setupTiming();
        RuntimeHelper.initialize();

        isLoading = true;
        setLoadingScreen(new LoadingScreen("loadingScreen"));
        Mouse.setGrabbed(true);

        ThreadHelper.startThread(new GameLoop()); //Physics and processing
        ThreadHelper.startThread(new RenderLoop()); //Graphics and rendering

    }

    public static void pauseGame() {

        Mouse.setGrabbed(false);
        isGamePaused = true;

        int menuDepartment;

        if (maxMenuGUIDepartments > 0) menuDepartment = 0;

        else menuDepartment = -1;

        activeMenuGUIDepartment = activeGUIDepartment = menuDepartment;

    }

    public static void unPauseGame() {

        Mouse.setGrabbed(true);
        isGamePaused = false;
        activeGUIDepartment = -1;

    }

    public static void stopGame() {

        AudioController.disposeAudioData();
        ThreadHelper.stopAllThreads();
        System.exit(0);

    }

}
