package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.Camera;
import mEngine.interactive.gameObjects.GameObjectRenderable;
import org.lwjgl.util.vector.Vector3f;

public class GameController {

    public static boolean isGamePaused;

    public static void runGame() {

        GraphicsController.createDisplay(1280, 720, 60, "mEngine Test Run", false);

        ObjectController.camera = new Camera(new Vector3f(), new Vector3f());

        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 0, 50), new Vector3f(), "res/assets/models/star.obj"));
        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 0, -50), new Vector3f(), "res/assets/models/star.obj"));
        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 50, 0), new Vector3f(), "res/assets/models/star.obj"));
        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, -50, 0), new Vector3f(), "res/assets/models/star.obj"));
        ObjectController.addObject(new GameObjectRenderable(new Vector3f(50, 0, 0), new Vector3f(), "res/assets/models/star.obj"));
        ObjectController.addObject(new GameObjectRenderable(new Vector3f(-50, 0, 0), new Vector3f(), "res/assets/models/star.obj"));

        GameLoop.loop();

    }

    public static void stopGame() {

        System.exit(0);

    }

}
