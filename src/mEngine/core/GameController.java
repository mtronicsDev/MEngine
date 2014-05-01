package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.CollideComponent;
import mEngine.gameObjects.components.MovementComponent;
import mEngine.gameObjects.components.controls.ControllerKeyboardMouse;
import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.gameObjects.components.gui.guiComponents.GUIQuad;
import mEngine.gameObjects.components.renderable.*;
import mEngine.graphics.GraphicsController;
import mEngine.graphics.renderable.LoadingScreen;
import mEngine.physics.forces.ForceController;
import mEngine.util.time.RuntimeHelper;
import mEngine.util.time.TimeHelper;
import mEngine.util.audio.AudioHelper;
import mEngine.util.debug.graphs.FPSGraphComponent;
import mEngine.util.debug.graphs.RAMGraphComponent;
import mEngine.util.debug.graphs.TPSGraphComponent;
import mEngine.util.debug.texts.FPSTextComponent;
import mEngine.util.debug.texts.RAMTextComponent;
import mEngine.util.debug.texts.TPSTextComponent;
import mEngine.util.debug.texts.position.PositionXTextComponent;
import mEngine.util.debug.texts.position.PositionYTextComponent;
import mEngine.util.debug.texts.position.PositionZTextComponent;
import mEngine.util.resources.PreferenceHelper;
import mEngine.util.resources.ResourceHelper;
import mEngine.util.threading.ThreadHelper;
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
        addGameObject(new GameObject(new Vector3f(0, 10, 0), new Vector3f())
                .addComponent(
                        "movementComponent",
                        new MovementComponent()
                )
                .addComponent(
                        "renderComponent",
                        new RenderComponent("texturedStar")
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
                        "lightSource",
                        new LightSource(100, new Vector4f(0, 1, 0, 1), new Vector3f(-1, -1, -1), 10)
                )
                .addComponent(
                        "fpsText",
                        new GUIElement(new Vector2f(0, 1), new Vector2f(), true).addComponent("guiText", new FPSTextComponent("Current FPS", 15))
                )
                .addComponent(
                        "tpsText",
                        new GUIElement(new Vector2f(0, 4), new Vector2f(), true).addComponent("guiText", new TPSTextComponent("Current TPS", 15))
                )
                .addComponent(
                        "ramText",
                        new GUIElement(new Vector2f(0, 7), new Vector2f(), true).addComponent("guiText", new RAMTextComponent("Current RAM", 15))
                )
                .addComponent(
                        "posXText",
                        new GUIElement(new Vector2f(0, 10), new Vector2f(), true).addComponent("guiText", new PositionXTextComponent("x Position", 15))
                )
                .addComponent(
                        "posYText",
                        new GUIElement(new Vector2f(0, 13), new Vector2f(), true).addComponent("guiText", new PositionYTextComponent("y Position", 15))
                )
                .addComponent(
                        "posZText",
                        new GUIElement(new Vector2f(0, 16), new Vector2f(), true).addComponent("guiText", new PositionZTextComponent("z Position", 15))
                )
                .addComponent(
                        "graphs",
                        new GUIElement(new Vector2f(0, ((Display.getHeight() - 100) * 100 / Display.getHeight())), new Vector2f(), false, true)
                                .addComponent("guiGraphFPS", new FPSGraphComponent(
                                        new Vector2f(100, 100),
                                        new double[]{},
                                        "graph"))
                                .addComponent("guiGraphTPS", new TPSGraphComponent(
                                        new Vector2f(100, 100),
                                        new double[]{},
                                        "graph"))
                                .addComponent("guiGraphRAM", new RAMGraphComponent(
                                        new Vector2f(100, 100),
                                        new double[]{},
                                        "graph"))
                )
                .addComponent(
                        "reticule",
                        new GUIElement(new Vector2f(50, 50), new Vector2f(64, 64), true)
                                .addComponent("guiQuad", new GUIQuad("reticule"))
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(0, 1, -20), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("12star")
                )
                .addComponent(
                        "lightSource",
                        new LightSource(100, new Vector4f(1, 1, 0.5f, 1))
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(20, 1, 10), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("sphere")
                )
                .addComponent(
                        "lightSource",
                        new LightSource(100, new Vector4f(1, 0.5f, 1, 1))
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(20, 1, -20), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("sphere")
                )
                .addComponent(
                        "lightSource",
                        new LightSource(100, new Vector4f(0.5f, 1, 1, 1))
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(0, 1, 10), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("sphere")
                )
                .addComponent(
                        "lightSource",
                        new LightSource(100, new Vector4f(1, 1, 0, 1))
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(40, 1, 40), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("sphere")
                )
                .addComponent(
                        "lightSource",
                        new LightSource(100, new Vector4f(1, 0, 1, 1))
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(45, 1, 0), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("sphere")
                )
                .addComponent(
                        "lightSource",
                        new LightSource(100, new Vector4f(0, 1, 1, 1))
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(0, 10, 40), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("sphere")
                )
                .addComponent(
                        "lightSource",
                        new LightSource(100, new Vector4f(1, 0, 0, 1))
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(-30, 0, -30), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("texturedStar")
                )
                .addComponent(
                        "terrain",
                        new Terrain(new Vector3f(100, 100, 100), true)
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(20, 40, 20), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("sphere")
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
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
