package mEngine.core;

import mEngine.graphics.GraphicsController;

public class GameController {

    public static boolean isGamePaused;

    public static void runGame() {

        GraphicsController.createDisplay(1280, 720, 60, "mEngine Test Run", false);
        GameLoop.loop();

    }
    public static void stopGame() {

        System.exit(0);

    }

}
