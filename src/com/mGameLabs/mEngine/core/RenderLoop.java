package com.mGameLabs.mEngine.core;

import com.mGameLabs.mEngine.gameObjects.GameObject;
import com.mGameLabs.mEngine.graphics.GraphicsController;
import com.mGameLabs.mEngine.graphics.RenderQueue;
import com.mGameLabs.mEngine.graphics.Renderer;
import com.mGameLabs.mEngine.util.input.Input;
import com.mGameLabs.mEngine.util.rendering.ShaderHelper;
import com.mGameLabs.mEngine.util.resources.PreferenceHelper;
import com.mGameLabs.mEngine.util.serialization.Serializer;
import com.mGameLabs.mEngine.util.time.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

public class RenderLoop implements Runnable {

    @Override
    public void run() {

        GraphicsController.createDisplay(PreferenceHelper.getValue("title"));
        ShaderHelper.addShader("lighting");

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            GraphicsController.clearScreen(new Vector4f(0.44f, 0.58f, 0.93f, 1));
            Renderer.currentRenderQueue = new RenderQueue();

            if (!GameController.isLoading) {

                if (!GameController.isGamePaused)
                    if (Input.isKeyDown(Keyboard.KEY_F2)) GraphicsController.takeScreenshot();

                if (!Serializer.isSerializing) {

                    //Renders all the gameObjects
                    for (GameObject object : ObjectController.gameObjects)
                        object.addToRenderQueue();

                }

                Renderer.currentRenderQueue.render();

            } else if (ObjectController.getLoadingScreen() != null) ObjectController.getLoadingScreen().render();

            TimeHelper.updateFPS();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
