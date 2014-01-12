package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.util.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

public class GameLoop {

    public static void loop() {

        while (!Display.isCloseRequested()) {

            GraphicsController.clearScreen(new Vector4f(0.44f, 0.58f, 0.93f, 1));

            if(Input.isKeyDown(Keyboard.KEY_ESCAPE)) GameController.isGamePaused = !GameController.isGamePaused;

            for(GameObject gameObject : ObjectController.objects) {

                gameObject.update();

            }

            ObjectController.camera.update();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
