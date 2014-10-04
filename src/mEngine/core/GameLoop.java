/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.physics.PhysicsController;
import mEngine.util.serialization.Serializer;
import mEngine.util.time.TimeHelper;
import org.lwjgl.opengl.Display;

public class GameLoop {

    /**
     * Runs the update loop
     */
    public static void startLoop() {

        //Waiting for Display creation
        while (!Display.isCreated()) {
            TimeHelper.sleep(10);
        }

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            TimeHelper.updateDeltaTime();
            if (ObjectController.getLoadingScreen() != null) ObjectController.getLoadingScreen().update();

            if (!GameController.isLoading) {

                PhysicsController.world.stepSimulation(TimeHelper.deltaTime);

                //Adds all new game objects that were added via ObjectController.addGameObject() to the game object list
                ObjectController.addNewGameObjects();

                if (!Serializer.isSerializing)
                    ObjectController.gameObjects.stream()
                      .forEach(GameObject::update);

            }

            TimeHelper.updateTPS();

        }

        GameController.stopGame();

    }

}
