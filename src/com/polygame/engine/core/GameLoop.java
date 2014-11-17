/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.core;

import com.polygame.engine.gameObjects.GameObject;
import com.polygame.engine.graphics.GraphicsController;
import com.polygame.engine.physics.PhysicsController;
import com.polygame.engine.util.serialization.Serializer;
import com.polygame.engine.util.time.TimeHelper;

import static org.lwjgl.system.glfw.GLFW.glfwPollEvents;

public class GameLoop {

    /**
     * Runs the update loop
     */
    public static void startLoop() {

        while (!GraphicsController.shouldClose() && !Thread.interrupted()) {

            TimeHelper.updateDeltaTime();

            if (!GameController.isLoading()) {

                PhysicsController.world.stepSimulation(TimeHelper.deltaTime);

                //Adds all new game objects that were added via ObjectController.addGameObject() to the game object list
                ObjectController.addNewGameObjects();
                //Removes all game objects that should be removed
                ObjectController.removeGameObjects();

                if (!Serializer.isSerializing)
                    ObjectController.gameObjects.forEach(GameObject::update);

            }
            TimeHelper.updateTPS();
        }
        GameController.stopGame();
    }

}
