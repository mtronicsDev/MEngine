package mEngine.core;

import mEngine.audio.AudioController;
import mEngine.gameObjects.components.AudioSource;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.*;
import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.gameObjects.components.gui.guiComponents.GUIQuad;
import mEngine.gameObjects.components.gui.guiComponents.GUIText;
import mEngine.graphics.GraphicsController;
import mEngine.physics.forces.ForceController;
import mEngine.util.PreferenceHelper;
import mEngine.util.ResourceHelper;
import mEngine.util.RuntimeHelper;
import mEngine.util.TimeHelper;
import mEngine.util.debug.FPSGraphComponent;
import mEngine.util.debug.FPSTextComponent;
import mEngine.util.debug.RAMGraphComponent;
import mEngine.util.debug.RAMTextComponent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static mEngine.core.ObjectController.*;

public class GameController {

    public static boolean isGamePaused;

    public static void runGame() {

        ResourceHelper.initialize();
        PreferenceHelper.loadPreferences("mEngine");
        GraphicsController.createDisplay(120, "mEngine Test Run");
        AudioController.initializeOpenAL();
        TimeHelper.setupTiming();
        RuntimeHelper.initialize();

        ForceController.addForce("gravity", new Vector3f(0, -9.81f, 0));

        //GameObject Time ;)
        GameObject object;

        addGameObject(new GameObject(new Vector3f(), new Vector3f()));
        object = getGameObject(gameObjects.size() - 1);

        object.addComponent(
                "renderComponent",
                new RenderComponent("texturedStar")
        );
        object.addComponent(
                "collideComponent",
                new CollideComponent(false, true)
        );
        object.addComponent(
                "movementComponent",
                new MovementComponent()
        );
        object.addComponent(
                "audioListener",
                new AudioListener(object)
        );
        object.addComponent(
                "controller",
                new ControllerKeyboardMouse(
                        new float[]{1, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 11},
                        false
                )
        );
        object.addComponent(
                "camera",
                new Camera()
        );
        object.addComponent(
                "fpsText",
                new GUIElement(new Vector2f(5, 5), new Vector2f()).addComponent("guiText", new FPSTextComponent("Current FPS", 15))
        );
        object.addComponent(
                "ramText",
                new GUIElement(new Vector2f(5, 25), new Vector2f()).addComponent("guiText", new RAMTextComponent("Current RAM", 15))
        );
        object.addComponent(
                "xText",
                new GUIElement(new Vector2f(5, 50), new Vector2f()).addComponent("guiText", new GUIText("x:", 15))
        );
        object.addComponent(
                "yText",
                new GUIElement(new Vector2f(5, 70), new Vector2f()).addComponent("guiText", new GUIText("y:", 15))
        );
        object.addComponent(
                "zText",
                new GUIElement(new Vector2f(5, 90), new Vector2f()).addComponent("guiText", new GUIText("z:", 15))
        );
        object.addComponent(
                "collisionText",
                new GUIElement(new Vector2f(5, 115), new Vector2f()).addComponent("guiText", new GUIText("Player collision:", 15))
        );
        object.addComponent(
                "graphs",
                new GUIElement(new Vector2f(0, Display.getHeight() - 100), new Vector2f())
                        .addComponent("guiGraphFPS", new FPSGraphComponent(
                                new Vector2f(Display.getWidth(), 100),
                                new double[]{}))
                        .addComponent("guiGraphRAM", new RAMGraphComponent(
                                new Vector2f(Display.getWidth(), 100),
                                new double[]{}))
        );
        object.addComponent(
                "reticule",
                new GUIElement(new Vector2f(Display.getWidth() / 2 - 32, Display.getHeight() / 2 - 32), new Vector2f(64, 64)).addComponent("guiQuad", new GUIQuad("reticule"))
        );
        object.addComponent(
                "background",
                new GUIElement(new Vector2f(0, 0), new Vector2f(1280, 720)).addComponent("background", new GUIQuad("background"))
        );
        object.addComponent(
                "icon",
                new GUIElement(new Vector2f(Display.getWidth() / 2 - 200, Display.getHeight() / 2 - 200), new Vector2f(400, 400)).addComponent("icon", new GUIQuad("icon"))
        );

        addGameObject(new GameObject(new Vector3f(0, 0, -20), new Vector3f()));
        object = getGameObject(gameObjects.size() - 1);

        object.addComponent(
                "renderComponent",
                new RenderComponent("12star")
        );
        object.addComponent(
                "collideComponent",
                new CollideComponent(false, true)
        );

        addGameObject(new GameObject(new Vector3f(0, 0, -30), new Vector3f()));
        object = getGameObject(gameObjects.size() - 1);

        object.addComponent(
                "renderComponent",
                new RenderComponent("texturedStar")
        );
        object.addComponent(
                "collideComponent",
                new CollideComponent(false, true)
        );

        addGameObject(new GameObject(new Vector3f(0, 0, -40), new Vector3f()));
        object = getGameObject(gameObjects.size() - 1);

        object.addComponent(
                "renderComponent",
                new RenderComponent("texturedStar")
        );
        object.addComponent(
                "collideComponent",
                new CollideComponent(false, true)
        );

        addGameObject(new GameObject(new Vector3f(0, 0, -5), new Vector3f(0, 0, 0)));
        object = getGameObject(gameObjects.size() - 1);

        object.addComponent(
                "renderComponent",
                new RenderComponent("texturedStar")
        );
        object.addComponent(
                "collideComponent",
                new CollideComponent(false, true)
        );

        ObjectController.addAudioSource(new AudioSource(ObjectController.getGameObject(2), "test"));

        AudioController.setListener(getGameObject(0));

        for (AudioSource source : audioSources) {
            source.play();
        }

        Mouse.setGrabbed(true);
        GameLoop.loop();

    }

    public static void pauseGame() {

        Mouse.setGrabbed(false);
        for (AudioSource source : audioSources) {
            source.pause();
        }

        isGamePaused = true;

    }

    public static void unPauseGame() {

        Mouse.setGrabbed(true);
        for (AudioSource source : audioSources) {
            source.play();
        }

        isGamePaused = false;

    }

    public static void stopGame() {

        AudioController.killALData();
        System.exit(0);

    }

}
