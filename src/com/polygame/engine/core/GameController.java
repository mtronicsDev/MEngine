/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.core;

import com.polygame.engine.core.events.EventController;
import com.polygame.engine.physics.PhysicsController;
import com.polygame.engine.util.debug.RuntimeHelper;
import com.polygame.engine.util.input.Input;
import com.polygame.engine.util.resources.PreferenceHelper;
import com.polygame.engine.util.resources.ResourceHelper;
import com.polygame.engine.util.threading.ThreadHelper;
import com.polygame.engine.util.time.TimeHelper;
import org.lwjgl.Sys;

public class GameController {

    private static boolean gamePaused;
    private static boolean loading;

    /**
     * Initializes all core classes and starts the game.
     */
    public static void runGame() {

        setLoading(true);
        Sys.touch();

        ResourceHelper.initialize();
        PreferenceHelper.loadPreferences("mEngine");
        Input.initialize();
        //TODO: AudioController.initialize();
        TimeHelper.initialize();
        RuntimeHelper.initialize();
        PhysicsController.initialize();

        EventController.triggerEvent("initialized");

        //ThreadHelper.startThread(GameLoop::startLoop, "polygameEngine-GameLoop"); //Activated by the render loop once ready
        ThreadHelper.startThread(RenderLoop::startLoop, "polygameEngine-RenderLoop"); //Graphics and rendering

    }

    /**
     * Returns if the game is paused or not.
     *
     * @return True if paused, false if not
     */
    public static boolean isGamePaused() {
        return gamePaused;
    }

    /**
     * Returns if the ObjectController is loading or not.
     *
     * @return True if loading, false if not.
     */
    public static boolean isLoading() {
        return loading;
    }

    /**
     * Sets the loading variable and triggers the "loadingStarted" and "loadingStopped" events.
     *
     * @param loading If the game should be loading.
     */
    public static void setLoading(boolean loading) {
        if (!GameController.loading && loading) EventController.triggerEvent("loadingStarted");
        else if (GameController.loading && !loading) EventController.triggerEvent("loadingStopped");

        GameController.loading = loading;
    }

    /**
     * Pauses all game objects (no updates, still renders them).
     */
    public static void pauseGame() {
        EventController.triggerEvent("gamePaused");
        gamePaused = true;
    }

    /**
     * Resumes tha game and the update loop for game objects.
     */
    public static void resumeGame() {
        gamePaused = false;
        EventController.triggerEvent("gameResumed");
    }

    /**
     * Clears all bindings and stops the game.
     */
    public static void stopGame() {
        EventController.triggerEvent("gameStopped");
        ThreadHelper.stopAllThreads();
        //AudioController.clear();
        System.exit(0);

    }

}
