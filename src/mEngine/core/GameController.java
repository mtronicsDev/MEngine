package mEngine.core;

import mEngine.audio.AudioController;
import mEngine.audio.AudioSource;
import mEngine.graphics.GraphicsController;
import mEngine.interactive.controls.KeyboardMouse;
import mEngine.interactive.gameObjects.Camera;
import mEngine.interactive.gameObjects.Player;
import mEngine.interactive.gui.GUIController;
import mEngine.interactive.gui.GUIEllipse;
import mEngine.interactive.gui.GUIQuad;
import mEngine.physics.ForceController;
import mEngine.util.PreferenceHelper;
import mEngine.util.RuntimeHelper;
import mEngine.util.TimeHelper;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import mEngine.interactive.gameObjects.GameObjectRenderable;

public class GameController {

    public static boolean isGamePaused;

    public static void runGame() {

        PreferenceHelper.loadPreferences("res/preferences/mEngine.mmp");
        GraphicsController.createDisplay(60, "mEngine Test Run");
        AudioController.initializeOpenAL();
        TimeHelper.setupTiming();
        RuntimeHelper.initialize();

        ForceController.addForce(new Vector3f(0, -9.81f, 0)); //Gravity
        ForceController.addForce(new Vector3f(0, 0, -5)); //Forward
        ForceController.addForce(new Vector3f(0, 0, 4)); //Backward
        ForceController.addForce(new Vector3f(4, 0, 0)); //Right
        ForceController.addForce(new Vector3f(-4, 0, 0)); //Left
        ForceController.addForce(new Vector3f(0, 4, 0)); //Up
        ForceController.addForce(new Vector3f(0, -4, 0)); //Down
        ForceController.addForce(new Vector3f(0, 10, 0)); //Jump force

        ObjectController.addObject(new Player(new Vector3f(0, 0, 0), new Vector3f(), "res/assets/models/texturedStar.obj", "res/assets/textures/texturedStar.png", new KeyboardMouse()));
        GUIController.guiElements.add(new GUIQuad(new Vector2f(50, 50), new Vector2f(80, 80)));
        GUIController.guiElements.add(new GUIEllipse(new Vector2f(300, 300), new Vector2f(80, 40)));

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
        AudioController.setListener(ObjectController.objects.get(0));

        /*try {

            ObjectController.addAudioSource(new AudioSource(ObjectController.objects.get(1)));

        }
        catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }*/

        for(AudioSource source : ObjectController.audioSources) { source.play(); }
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
        for(AudioSource source : ObjectController.audioSources) { source.play(); }

        isGamePaused = false;

    }

    public static void stopGame() {

        AudioController.killALData();
        System.exit(0);

    }

}
