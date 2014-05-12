package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.controls.ControllerKeyboardMouse;
import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.gameObjects.components.gui.guiComponents.GUIQuad;
import mEngine.gameObjects.components.interaction.InteractionComponent;
import mEngine.gameObjects.components.interaction.methods.AsyncMethod;
import mEngine.gameObjects.components.physics.CollideComponent;
import mEngine.gameObjects.components.physics.MovementComponent;
import mEngine.gameObjects.components.renderable.Camera;
import mEngine.gameObjects.components.renderable.RenderComponent;
import mEngine.gameObjects.components.renderable.Skybox;
import mEngine.gameObjects.components.renderable.light.GlobalLightSource;
import mEngine.graphics.renderable.LoadingScreen;
import mEngine.physics.collisions.Collision;
import mEngine.physics.forces.ForceController;
import mEngine.util.audio.AudioHelper;
import mEngine.util.debug.RuntimeHelper;
import mEngine.util.debug.texts.*;
import mEngine.util.debug.texts.position.PositionXTextComponent;
import mEngine.util.debug.texts.position.PositionYTextComponent;
import mEngine.util.debug.texts.position.PositionZTextComponent;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.resources.PreferenceHelper;
import mEngine.util.resources.ResourceHelper;
import mEngine.util.threading.ThreadHelper;
import mEngine.util.time.TimeHelper;
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
        addGameObject(new GameObject(new Vector3f(-67.8f, 23.0f, -148.7f), new Vector3f(-11.9f, 153.3f, 0))
                .addComponent(
                        "movementComponent",
                        new MovementComponent()
                )
                .addComponent(
                        "renderComponent",
                        new RenderComponent("sphere", false)
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
                        new CollideComponent(false, new Collision() {
                            @Override
                            public void onCollision() {}
                        })
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
                        new GUIElement(new Vector2f(Display.getWidth() / 2 - 32, Display.getHeight() / 2 - 32), new Vector2f(64, 64), "reticule")
                                .addComponent("guiQuad", new GUIQuad())
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
                .addComponent(
                        "vertexCountText",
                        new GUIElement(new Vector2f(5, 165), new Vector2f()).addComponent("guiText", new VertexCountTextComponent("vertices", 15))
                )
                .addComponent(
                        "faceCountText",
                        new GUIElement(new Vector2f(5, 185), new Vector2f()).addComponent("guiText", new FaceCountTextComponent("faces", 15))
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(0, 80, 0), new Vector3f())
                .addComponent(
                        "renderComponent",
                        new RenderComponent("monkey", false)
                )
                .addComponent(
                        "interactionComponent",
                        new InteractionComponent(true, 10, "I", "move monkey", 25, new AsyncMethod() {
                            @Override
                            public void interact() {
                                caller.interactable = false;

                                for (int count = 0; count < 1000; count++) {

                                    parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0.005f, 0.005f, 0.005f)});

                                    try {

                                        Thread.sleep(10);

                                    } catch (InterruptedException e) {

                                        e.printStackTrace();
                                        ThreadHelper.stopAllThreads();
                                        System.exit(1);

                                    }

                                }

                                caller.interactable = true;

                            }
                        })
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(), new Vector3f())
                .addComponent(
                        "ocean",
                        new RenderComponent("sciFiOcean", new Vector3f(0, -84, 0))
                )
                .addComponent(
                        "city",
                        new RenderComponent("sciFiCity", new Vector3f(0, 50, 0))
                )
                .addComponent(
                        "terrain",
                        new RenderComponent("sciFiTerrain")
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(), new Vector3f(35, -45, 0))
                .addComponent(
                        "sunAmbient0",
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(0, 1, 0))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        "sunAmbient1",
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(0, -1, 0))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        "sunAmbient2",
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(1, 0, 0))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        "sunAmbient3",
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(-1, 0, 0))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        "sunAmbient4",
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(0, 0, 1))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        "sunAmbient5",
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(0, 0, -1))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        "sun",
                        new GlobalLightSource(15, new Vector4f(255, 251, 237, 1), new Vector3f(0, -1, 0))
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
