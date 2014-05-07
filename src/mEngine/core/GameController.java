package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.controls.ControllerKeyboardMouse;
import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.gameObjects.components.gui.guiComponents.GUIQuad;
import mEngine.gameObjects.components.interaction.Interaction;
import mEngine.gameObjects.components.interaction.InteractionComponent;
import mEngine.gameObjects.components.physics.CollideComponent;
import mEngine.gameObjects.components.physics.MovementComponent;
import mEngine.gameObjects.components.renderable.Camera;
import mEngine.gameObjects.components.renderable.LightSource;
import mEngine.gameObjects.components.renderable.RenderComponent;
import mEngine.gameObjects.components.renderable.Skybox;
import mEngine.graphics.renderable.LoadingScreen;
import mEngine.physics.forces.ForceController;
import mEngine.util.audio.AudioHelper;
import mEngine.util.debug.texts.FPSTextComponent;
import mEngine.util.debug.texts.RAMTextComponent;
import mEngine.util.debug.texts.TPSTextComponent;
import mEngine.util.debug.texts.position.PositionXTextComponent;
import mEngine.util.debug.texts.position.PositionYTextComponent;
import mEngine.util.debug.texts.position.PositionZTextComponent;
import mEngine.util.input.Input;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.resources.PreferenceHelper;
import mEngine.util.resources.ResourceHelper;
import mEngine.util.threading.ThreadHelper;
import mEngine.util.time.RuntimeHelper;
import mEngine.util.time.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import static mEngine.core.ObjectController.addGameObject;
import static mEngine.core.ObjectController.setLoadingScreen;

public class GameController {

    public static boolean isGamePaused;
    public static boolean isLoading;

    public static void runGame() {

        ResourceHelper.initialize();
        PreferenceHelper.loadPreferences("mEngine");
        AudioHelper.initializeOpenAL();
        TimeHelper.setupTiming();
        RuntimeHelper.initialize();

        isLoading = true;
        setLoadingScreen(new LoadingScreen("loadingScreen"));
        Mouse.setGrabbed(true);

        ThreadHelper.startThread(new GameLoop()); //Physics and processing
        ThreadHelper.startThread(new RenderLoop()); //Graphics and rendering

        ForceController.addForce("gravity", new Vector3f(0, -9.81f, 0));

        //GameObject Time ;)
        addGameObject(new GameObject(new Vector3f(0, 15, 0), new Vector3f())
                .addComponent(
                        "movementComponent",
                        new MovementComponent()
                )
                .addComponent(
                        "renderComponent",
                        new RenderComponent("sphere")
                )
                .addComponent(
                        "controller",
                        new ControllerKeyboardMouse(
                                new float[]{0.3f, 0.23f, 0.23f, 0.23f, 0.23f, 0.23f, 0.23f, 11},
                                true
                        )
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
                )
                .addComponent(
                        "skybox",
                        new Skybox("peaks")
                )
                .addComponent(
                        "camera",
                        new Camera()
                )
                .addComponent(
                        "reticule",
                        new GUIElement(new Vector2f(Display.getWidth() / 2 - 32, Display.getHeight() / 2 - 32), new Vector2f(64, 64))
                                .addComponent("guiQuad", new GUIQuad("reticule"))
                )
                .addComponent(
                        "fpsText",
                        new GUIElement(new Vector2f(5, 5), new Vector2f()).addComponent("guiText", new FPSTextComponent("Current FPS", 15))
                )
                .addComponent(
                        "tpsText",
                        new GUIElement(new Vector2f(5, 25), new Vector2f()).addComponent("guiText", new TPSTextComponent("Current TPS", 15))
                )
                .addComponent(
                        "ramText",
                        new GUIElement(new Vector2f(5, 45), new Vector2f()).addComponent("guiText", new RAMTextComponent("Current RAM", 15))
                )
                .addComponent(
                        "posXText",
                        new GUIElement(new Vector2f(5, 85), new Vector2f()).addComponent("guiText", new PositionXTextComponent("x Position", 15))
                )
                .addComponent(
                        "posYText",
                        new GUIElement(new Vector2f(5, 105), new Vector2f()).addComponent("guiText", new PositionYTextComponent("y Position", 15))
                )
                .addComponent(
                        "posZText",
                        new GUIElement(new Vector2f(5, 125), new Vector2f()).addComponent("guiText", new PositionZTextComponent("z Position", 15))
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(0, 40, 0), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("monkey")
                )
                .addComponent(
                        "interactionComponent",
                        new InteractionComponent(true, 10, "I", "move monkey", new Interaction() {
                            @Override
                            public void interact(GameObject object) {
                                object.position = VectorHelper.sumVectors(new Vector3f[]{object.position, new Vector3f(5, 5, 5)});
                            }
                        })
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("Paris2010")
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(0, 500, 0), new Vector3f())
                .addComponent(
                        "sunAmbient",
                        new LightSource(800, new Vector4f(255, 147, 41, 1), new Vector3f(0, -1, 0), false)
                )
                .addComponent(
                        "sun",
                        new LightSource(4000, new Vector4f(255, 147, 41, 1), new Vector3f(-1, -1, 0))
                )
                .createAllComponents());

        isLoading = false;

    }

    public static void pauseGame() {

        Mouse.setGrabbed(false);
        isGamePaused = true;

    }

    public static void unPauseGame() {

        Mouse.setGrabbed(true);
        isGamePaused = false;

    }

    public static void stopGame() {

        AudioHelper.killALData();
        ThreadHelper.stopAllThreads();
        System.exit(0);

    }

}
