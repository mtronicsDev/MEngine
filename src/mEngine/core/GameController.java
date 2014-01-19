package mEngine.core;

import mEngine.audio.AudioSource;
import mEngine.graphics.GraphicsController;
import mEngine.interactive.controls.ArtificialIntelligence;
import mEngine.interactive.controls.KeyboardMouse;
import mEngine.interactive.gameObjects.Camera;
import mEngine.interactive.gameObjects.Player;
import mEngine.physics.ForceController;
import mEngine.util.PreferenceHelper;
import mEngine.util.RuntimeHelper;
import mEngine.util.TimeHelper;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.util.vector.Vector3f;
import mEngine.interactive.gameObjects.GameObjectRenderable;

public class GameController {

    public static boolean isGamePaused;

    public static void runGame() {

        PreferenceHelper.loadPreferences("res/preferences/mEngine.mmp");
        GraphicsController.createDisplay(1280, 720, 60, "mEngine Test Run", false);
        TimeHelper.setupTiming();
        RuntimeHelper.initialize();

        ForceController.addForce(new Vector3f(0, -9.81f, 0)); //Gravity
        ForceController.addForce(new Vector3f(0, 0, -10)); //Forward
        ForceController.addForce(new Vector3f(0, 0, 8)); //Backward
        ForceController.addForce(new Vector3f(8, 0, 0)); //Right
        ForceController.addForce(new Vector3f(-8, 0, 0)); //Left
        ForceController.addForce(new Vector3f(0, 8, 0)); //Up
        ForceController.addForce(new Vector3f(0, -8, 0)); //Down
        ForceController.addForce(new Vector3f(0, 0, 1)); //FwFriction
        ForceController.addForce(new Vector3f(0, 0, -0.8f)); //BwFriction
        ForceController.addForce(new Vector3f(-0.8f, 0, 0)); //RiF
        ForceController.addForce(new Vector3f(0.8f, 0, 0)); //LeF
        ForceController.addForce(new Vector3f(0, -0.8f, 0)); //UpF
        ForceController.addForce(new Vector3f(0, 0.8f, 0)); //DoF
        ForceController.addForce(new Vector3f(0, 10, 0)); //Jump force

        ObjectController.addObject(new Player(new Vector3f(0, 0, 0), new Vector3f(), "res/assets/models/texturedStar.obj", "res/assets/textures/texturedStar.png", new KeyboardMouse()));

        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 0, 50),
                new Vector3f(),
                "res/assets/models/texturedStar.obj",
                "res/assets/textures/texturedStar.png",
                null));

        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 0, -50),
                new Vector3f(),
                "res/assets/models/texturedStar.obj",
                "res/assets/textures/texturedStar.png",
                null));

        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, 50, 0),
                new Vector3f(),
                "res/assets/models/texturedStar.obj",
                "res/assets/textures/texturedStar.png",
                null));

        ObjectController.addObject(new GameObjectRenderable(new Vector3f(0, -50, 0),
                new Vector3f(),
                "res/assets/models/texturedStar.obj",
                "res/assets/textures/texturedStar.png",
                null));

        ObjectController.addObject(new GameObjectRenderable(new Vector3f(50, 0, 0),
                new Vector3f(),
                "res/assets/models/texturedStar.obj",
                "res/assets/textures/texturedStar.png",
                null));

        ObjectController.addObject(new GameObjectRenderable(new Vector3f(-50, 0, 0),
                new Vector3f(),
                "res/assets/models/texturedStar.obj",
                "res/assets/textures/texturedStar.png",
                null));

        ObjectController.camera = new Camera(ObjectController.objects.get(0));

        try {

            ObjectController.addAudioSource(new AudioSource(ObjectController.objects.get(1), ObjectController.objects.get(0)));

        }
        catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

        for(AudioSource source : ObjectController.audioSources) { //source.play();
        }
        Mouse.setGrabbed(true);

        GameLoop.loop();

    }

    public static void pauseGame() {

        Mouse.setGrabbed(false);
        for(AudioSource source : ObjectController.audioSources) { source.pause(); }

        isGamePaused = true;

    }

    public static void unPauseGame() {

        Mouse.setGrabbed(true);
        for(AudioSource source : ObjectController.audioSources) { //source.play();
        }

        isGamePaused = false;

    }

    public static void stopGame() {

        AL.destroy();
        System.exit(0);

    }

}
