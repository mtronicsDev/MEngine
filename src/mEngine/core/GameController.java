package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.CollideComponent;
import mEngine.gameObjects.components.MovementComponent;
import mEngine.gameObjects.components.controls.ControllerKeyboardMouse;
import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.gameObjects.components.gui.guiComponents.GUIQuad;
import mEngine.gameObjects.components.renderable.*;
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

public class GameController {

    public static boolean isGamePaused;

    public static void runGame() {

        ResourceHelper.initialize();
        PreferenceHelper.loadPreferences("mEngine");
        AudioHelper.initializeOpenAL();
        TimeHelper.setupTiming();
        RuntimeHelper.initialize();

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
                        new LightSource(100, new Vector4f(0, 1, 0, 1))
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
                        new GUIElement(new Vector2f(5, 70), new Vector2f()).addComponent("guiText", new PositionXTextComponent("x Position", 15))
                )
                .addComponent(
                        "posYText",
                        new GUIElement(new Vector2f(5, 90), new Vector2f()).addComponent("guiText", new PositionYTextComponent("y Position", 15))
                )
                .addComponent(
                        "posZText",
                        new GUIElement(new Vector2f(5, 110), new Vector2f()).addComponent("guiText", new PositionZTextComponent("z Position", 15))
                )
                .addComponent(
                        "graphs",
                        new GUIElement(new Vector2f(0, Display.getHeight() - 100), new Vector2f())
                                .addComponent("guiGraphFPS", new FPSGraphComponent(
                                        new Vector2f(Display.getWidth(), 100),
                                        new double[]{},
                                        "graph"))
                                .addComponent("guiGraphTPS", new TPSGraphComponent(
                                        new Vector2f(Display.getWidth(), 100),
                                        new double[]{},
                                        "graph"))
                                .addComponent("guiGraphRAM", new RAMGraphComponent(
                                        new Vector2f(Display.getWidth(), 100),
                                        new double[]{},
                                        "graph"))
                )
                .addComponent(
                        "reticule",
                        new GUIElement(new Vector2f(Display.getWidth() / 2 - 32, Display.getHeight() / 2 - 32), new Vector2f(64, 64))
                                .addComponent("guiQuad", new GUIQuad("reticule"))
                ));

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
                ));

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
                ));

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
                ));

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
                ));

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
                ));

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
                ));

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
                ));

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
                ));

        addGameObject(new GameObject(new Vector3f(20, 40, 20), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("sphere")
                )
                .addComponent(
                        "collideComponent",
                        new CollideComponent(false, true)
                ));

        Mouse.setGrabbed(true);

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
