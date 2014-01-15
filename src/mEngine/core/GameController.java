package mEngine.core;

import mEngine.audio.AudioSource;
import mEngine.graphics.GraphicsController;
import mEngine.interactive.controls.KeyboardMouse;
import mEngine.interactive.gameObjects.Camera;
import mEngine.interactive.gameObjects.Player;
import mEngine.physics.ForceController;
import mEngine.util.PreferenceHelper;
import mEngine.util.TimeHelper;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.vector.Vector3f;
import mEngine.interactive.gameObjects.GameObjectRenderable;

public class GameController {

    public static boolean isGamePaused;

    public static void runGame() {

        PreferenceHelper.loadPreferences("res/preferences/mEngine.mmp");
        GraphicsController.createDisplay(1280, 720, 60, "mEngine Test Run", false);
        TimeHelper.setupTiming();

        ForceController.addForce(new Vector3f(0, -9.81f, 0));
        ForceController.addForce(new Vector3f(0, 0, -10));
        ForceController.addForce(new Vector3f(0, 0, 8));
        ForceController.addForce(new Vector3f(8, 0, 0));
        ForceController.addForce(new Vector3f(-8, 0, 0));
        ForceController.addForce(new Vector3f(0, 8, 0));
        ForceController.addForce(new Vector3f(0, -8, 0));
        ForceController.addForce(new Vector3f(0, 0, 1));
        ForceController.addForce(new Vector3f(0, 0, -0.8f));
        ForceController.addForce(new Vector3f(-0.8f, 0, 0));
        ForceController.addForce(new Vector3f(0.8f, 0, 0));
        ForceController.addForce(new Vector3f(0, -0.8f, 0));
        ForceController.addForce(new Vector3f(0, 0.8f, 0));
        ForceController.addForce(new Vector3f(0, 10, 0));

        ObjectController.addObject(new Player(new Vector3f(0, 0, 0), new Vector3f(), "res/assets/models/texturedStar.obj", "res/assets/textures/texturedStar.png", new KeyboardMouse()));

        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 0, 50), new Vector3f(), "res/assets/models/star.obj"));
        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 0, -50), new Vector3f(), "res/assets/models/texturedStar.obj", "res/assets/textures/texturedStar.png"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 50, 0), new Vector3f(), "res/assets/models/star.obj"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, -50, 0), new Vector3f(), "res/assets/models/star.obj"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(50, 0, 0), new Vector3f(), "res/assets/models/star.obj"));
        //ObjectController.addObject(new GameObjectRenderable(new Vector3f(-50, 0, 0), new Vector3f(), "res/assets/models/star.obj"));

        ObjectController.camera = new Camera(ObjectController.objects.get(0));

        try {

            AudioSource source = new AudioSource(ObjectController.objects.get(1), ObjectController.objects.get(0));
            source.play();

        }
        catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

        GameLoop.loop();

    }

    public static void stopGame() {

        AL.destroy();
        System.exit(0);

    }

}
