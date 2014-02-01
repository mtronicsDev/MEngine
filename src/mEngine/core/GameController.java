package mEngine.core;

import mEngine.audio.AudioController;
import mEngine.audio.AudioSource;
import mEngine.graphics.GraphicsController;
import mEngine.interactive.controls.KeyboardMouse;
import mEngine.interactive.gameObjects.Camera;
import mEngine.interactive.gameObjects.GameObjectRenderable;
import mEngine.interactive.gameObjects.Player;
import mEngine.interactive.gui.GUIText;
import mEngine.interactive.gui.primitives.GUICircle;
import mEngine.physics.ForceController;
import mEngine.util.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

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

        TextureHelper.loadTexture("texturedStar");

        ObjectController.addGameObject(new Player(new Vector3f(0, 0, 0), new Vector3f(), "texturedStar", new float[] {5, 4, 4, 4, 4, 4, 10}, new KeyboardMouse(), false));
        //ObjectController.addGameObject(new GameObjectInvisible(new Vector3f(), new Vector3f(), new float[] {5, 4, 4, 4, 4, 4, 10}, new KeyboardMouse(), true));

        ObjectController.addGameObject(new Camera(ObjectController.getGameObject(0)));

        ObjectController.addGUIElement(new GUICircle(new Vector2f(Display.getWidth() / 2, Display.getHeight() / 2), 20));
        ObjectController.addGUIElement(new GUIText(new Vector2f(5, 5), "Current FPS", 15));
        ObjectController.addGUIElement(new GUIText(new Vector2f(5, 25), "Current RAM", 15));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(0, 0, 50),
                new Vector3f(),
                "texturedStar",
                null,
                false));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(0, 0, -50),
                new Vector3f(),
                "texturedStar",
                null,
                false));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(0, 50, 0),
                new Vector3f(),
                "texturedStar",
                null,
                false));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(0, -50, 0),
                new Vector3f(),
                "texturedStar",
                null,
                false));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(50, 0, 0),
                new Vector3f(),
                "texturedStar",
                null,
                false));

        ObjectController.addGameObject(new GameObjectRenderable(new Vector3f(-50, 0, 0),
                new Vector3f(),
                "texturedStar",
                null,
                false));

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
