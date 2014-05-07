package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.GraphicsController;
import mEngine.graphics.RenderQueue;
import mEngine.graphics.Renderer;
import mEngine.util.input.Input;
import mEngine.util.rendering.ShaderHelper;
import mEngine.util.resources.PreferenceHelper;
import mEngine.util.serialization.Serializer;
import mEngine.util.time.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

public class RenderLoop implements Runnable {

    @Override
    public void run() {

        GraphicsController.createDisplay(PreferenceHelper.getValue("title"));
        ShaderHelper.addShader("intenseColor");
        ShaderHelper.addShader("lighting");

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            GraphicsController.clearScreen(new Vector4f(0.44f, 0.58f, 0.93f, 1));
            Renderer.currentRenderQueue = new RenderQueue();

            if (!GameController.isLoading) {

                if (Input.isKeyDown(Keyboard.KEY_F2)) GraphicsController.takeScreenshot();

                //Renders all the gameObjects
                for (GameObject object : ObjectController.gameObjects) {

                    if (!Serializer.isSerializing) object.addToRenderQueue();

                }

                Renderer.currentRenderQueue.render();

            } else if (ObjectController.getLoadingScreen() != null) ObjectController.getLoadingScreen().render();

            TimeHelper.updateFPS();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
