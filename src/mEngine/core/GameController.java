package mEngine.core;

import mEngine.audio.AudioController;
import mEngine.audio.AudioSource;
import mEngine.graphics.GraphicsController;
import mEngine.interactive.controls.KeyboardMouse;
import mEngine.interactive.gameObjects.Camera;
import mEngine.interactive.gameObjects.GameObjectInvisible;
import mEngine.interactive.gameObjects.Player;
import mEngine.interactive.gui.primitives.GUICircle;
import mEngine.interactive.gui.primitives.GUIQuad;
import mEngine.physics.ForceController;
import mEngine.util.PreferenceHelper;
import mEngine.util.ResourceHelper;
import mEngine.util.RuntimeHelper;
import mEngine.util.TimeHelper;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import mEngine.interactive.gameObjects.GameObjectRenderable;

public class GameController {

    public static boolean isGamePaused;

    public static void runGame() {

        PreferenceHelper.loadPreferences("res/preferences/mEngine.mmp");
        ResourceHelper.initialize();
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

        ObjectController.addGameObject(new Player(new Vector3f(0, 0, 0), new Vector3f(), "texturedStar", new KeyboardMouse()));
        //ObjectController.addGameObject(new GameObjectInvisible(new Vector3f(), new Vector3f(), new KeyboardMouse()));

        ObjectController.addGameObject(new Camera(ObjectController.getGameObject(0)));

        ObjectController.addGUIElement(new GUICircle(new Vector2f(Display.getWidth() / 2, Display.getHeight() / 2), 40));
        ObjectController.addGUIElement(new GUIQuad(new Vector2f(), new Vector2f(100, 100)));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(0, 0, 50),
                new Vector3f(),
                "texturedStar",
                null));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(0, 0, -50),
                new Vector3f(),
                "texturedStar",
                null));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(0, 50, 0),
                new Vector3f(),
                "texturedStar",
                null));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(0, -50, 0),
                new Vector3f(),
                "texturedStar",
                null));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(50, 0, 0),
                new Vector3f(),
                "texturedStar",
                null));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(-50, 0, 0),
                new Vector3f(),
                "texturedStar",
                null));

        AudioController.setListener(ObjectController.getGameObject(0));

        /*try {

            ObjectController.addAudioSource(new AudioSource(ObjectController.getGameObject(1), "test"));

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
