package mEngine.core;

import mEngine.graphics.GraphicsController;
import org.lwjgl.opengl.Display;

public class GameLoop {

    public static void loop() {

        while (!Display.isCloseRequested()) {

            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
