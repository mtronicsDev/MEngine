/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.core;

import com.polygame.engine.gameObjects.GameObject;
import com.polygame.engine.graphics.GraphicsController;
import com.polygame.engine.graphics.RenderQueue;
import com.polygame.engine.graphics.Renderer;
import com.polygame.engine.util.rendering.ShaderHelper;
import com.polygame.engine.util.serialization.Serializer;
import com.polygame.engine.util.threading.ThreadHelper;
import com.polygame.engine.util.time.TimeHelper;

import static com.polygame.engine.core.events.EventController.addEventHandler;
import static com.polygame.engine.core.events.EventController.triggerEvent;

public class RenderLoop {

    /**
     * Creates a new OpenGL window, sets the standard shader and runs the render loop
     */
    public static void startLoop() {

        GraphicsController.createDisplay();
        addEventHandler("renderLoopInitialized", () -> ThreadHelper.startThread(GameLoop::startLoop, "polygameEngine-GameLoop"));
        triggerEvent("renderLoopInitialized");
        ShaderHelper.addShader("lighting");

        while (GraphicsController.shouldClose() && !Thread.interrupted()) {

            GraphicsController.clearScreen();
            Renderer.currentRenderQueue = new RenderQueue();

            if (!GameController.isLoading()) {
                if (!Serializer.isSerializing) {

                    //Renders all GameObjects
                    for (GameObject object : ObjectController.gameObjects)
                        object.addToRenderQueue();

                }
                Renderer.currentRenderQueue.render();
            }

            ObjectController.getLoadingScreen().render();

            TimeHelper.updateFPS();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
