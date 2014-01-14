package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.Camera;
import mEngine.util.PreferenceHelper;
import mEngine.util.TimeHelper;
import org.lwjgl.util.vector.Vector3f;
import mEngine.interactive.gameObjects.GameObjectRenderable;

public class GameController {

    public static boolean isGamePaused;

    public static void runGame() {

        PreferenceHelper.loadPreferences("res/preferences/mEngine.mmp");
        GraphicsController.createDisplay(1280, 720, 60, "mEngine Test Run", false);
        TimeHelper.setupTiming();

        ObjectController.camera = new Camera(new Vector3f(), new Vector3f());

        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 0, 50), new Vector3f(), "res/assets/models/star.obj"));
        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 0, -50), new Vector3f(), "res/assets/models/star.obj"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 50, 0), new Vector3f(), "res/assets/models/star.obj"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, -50, 0), new Vector3f(), "res/assets/models/star.obj"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(50, 0, 0), new Vector3f(), "res/assets/models/star.obj"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(-50, 0, 0), new Vector3f(), "res/assets/models/star.obj"));

        GameLoop.loop();

    }

    public static void stopGame() {

        System.exit(0);

    }

}
