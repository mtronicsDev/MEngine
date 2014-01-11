package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.Camera;
import mEngine.interactive.gameObjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    public static boolean isGamePaused;

    public static List<GameObject> gameObjects = new ArrayList<GameObject>();

    public static Camera camera;

    public static void runGame() {

        GraphicsController.createDisplay(1280, 720, 60, "mEngine Test Run", false);

        GameLoop.loop();

    }

    public static void stopGame() {

        System.exit(0);

    }

}
