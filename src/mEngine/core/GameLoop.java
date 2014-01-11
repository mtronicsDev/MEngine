package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.util.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class GameLoop {

    public static void loop() {

        while (!Display.isCloseRequested()) {

            if(Input.isKeyDown(Keyboard.KEY_ESCAPE)) GameController.isGamePaused = !GameController.isGamePaused;

            for(GameObject gameObject : GameController.gameObjects) {

                gameObject.update();

            }

            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
