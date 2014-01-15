package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.controls.KeyboardMouse;
import mEngine.interactive.gameObjects.Camera;
import mEngine.interactive.gameObjects.Player;
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

        ObjectController.addObject(new Player(new Vector3f(0, 0, 0), new Vector3f(), "res/assets/models/texturedStar.obj", "res/assets/textures/texturedStar.png", new KeyboardMouse()));

        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 0, 50), new Vector3f(), "res/assets/models/star.obj"));
        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 0, -50), new Vector3f(), "res/assets/models/texturedStar.obj", "res/assets/textures/texturedStar.png"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 50, 0), new Vector3f(), "res/assets/models/star.obj"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, -50, 0), new Vector3f(), "res/assets/models/star.obj"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(50, 0, 0), new Vector3f(), "res/assets/models/star.obj"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(-50, 0, 0), new Vector3f(), "res/assets/models/star.obj"));

        ObjectController.camera = new Camera(ObjectController.objects.get(0));

        GameLoop.loop();

    }

    public static void stopGame() {

        System.exit(0);

    }

}
