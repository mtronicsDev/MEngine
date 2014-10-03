/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.core;

import mEngine.audio.AudioController;
import mEngine.physics.PhysicsController;
import mEngine.util.debug.RuntimeHelper;
import mEngine.util.resources.PreferenceHelper;
import mEngine.util.resources.ResourceHelper;
import mEngine.util.threading.ThreadHelper;
import mEngine.util.time.TimeHelper;
import org.lwjgl.input.Mouse;

import static mEngine.core.events.EventController.addEvent;
import static mEngine.core.events.EventController.triggerEvent;

public class GameController {

    public static boolean isGamePaused;
    public static boolean isLoading;

    /**
     * Initializes all core classes and starts the game
     */
    public static void runGame() {

        isLoading = true;

        ResourceHelper.initialize();
        PreferenceHelper.loadPreferences("mEngine");
        AudioController.initialize();
        TimeHelper.initialize();
        RuntimeHelper.initialize();
        PhysicsController.initialize();

        addEvent("gamePaused");  //Gets triggered every time the game is paused
        addEvent("gameResumed"); //Every time the game is resumed
        addEvent("gameStopped"); //When the game is stopped

        ThreadHelper.startThread(new GameLoop()); //Physics and processing
        ThreadHelper.startThread(new RenderLoop()); //Graphics and rendering

    }

    /**
     * Pauses all game objects (no updates, still renders them)
     */
    public static void pauseGame() {
        triggerEvent("gamePaused");
        isGamePaused = true;
    }

    /**
     * Resumes tha game and the update loop for game objects
     */
    public static void resumeGame() {
        Mouse.setGrabbed(true);
        isGamePaused = false;
        triggerEvent("gameResumed");
    }

    /**
     * Clears all bindings and stops the game
     */
    public static void stopGame() {
        triggerEvent("gameStopped");
        AudioController.clear();
        ThreadHelper.stopAllThreads();
        System.exit(0);

    }

}
