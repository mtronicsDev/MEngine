/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.util.serialization.Serializer;
import mEngine.util.time.TimeHelper;
import org.lwjgl.opengl.Display;

public class GameLoop implements Runnable {

    /**
     * Runs the update loop
     */
    public void run() {

        //noinspection StatementWithEmptyBody
        while (!Display.isCreated()) {
        } //Waiting for Display creation

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            TimeHelper.updateDeltaTime();
            if (ObjectController.getLoadingScreen() != null) ObjectController.getLoadingScreen().update();

            if (!GameController.isLoading) {

                for (GameObject gameObject : ObjectController.gameObjects) {

                    if (!Serializer.isSerializing) gameObject.update();

                }

            }

            TimeHelper.updateTPS();

        }

        GameController.stopGame();

    }

}
