package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.controls.ControllerKeyboardMouse;
import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.gameObjects.components.gui.guiComponents.GUIQuad;
import mEngine.gameObjects.components.gui.guiComponents.GUIText;
import mEngine.gameObjects.components.gui.guiComponents.buttons.ButtonPressingMethod;
import mEngine.gameObjects.components.gui.guiComponents.buttons.GUIButton;
import mEngine.gameObjects.components.interaction.InteractionComponent;
import mEngine.gameObjects.components.interaction.methods.AsyncMethod;
import mEngine.gameObjects.components.physics.MovementComponent;
import mEngine.gameObjects.components.physics.PhysicComponent;
import mEngine.gameObjects.components.renderable.Camera;
import mEngine.gameObjects.components.renderable.RenderComponent;
import mEngine.gameObjects.components.renderable.Skybox;
import mEngine.gameObjects.components.renderable.light.GlobalLightSource;
import mEngine.graphics.renderable.LoadingScreen;
import mEngine.graphics.renderable.materials.Material;
import mEngine.graphics.renderable.materials.Material2D;
import mEngine.physics.forces.ForceController;
import mEngine.util.audio.AudioHelper;
import mEngine.util.debug.RuntimeHelper;
import mEngine.util.debug.texts.*;
import mEngine.util.debug.texts.position.PositionXTextComponent;
import mEngine.util.debug.texts.position.PositionYTextComponent;
import mEngine.util.debug.texts.position.PositionZTextComponent;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.physics.CollisionHelper;
import mEngine.util.resources.PreferenceHelper;
import mEngine.util.resources.ResourceHelper;
import mEngine.util.threading.ThreadHelper;
import mEngine.util.time.TimeHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.Color;

import static mEngine.core.ObjectController.*;

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

        maxMenuGUIDepartments = PreferenceHelper.getInteger("maxMenuDepartments");
        activeMenuGUIDepartment = maxMenuGUIDepartments - 1;
        activeGUIDepartment = -1;

        //GameObject Time ;)
        addGameObject(new GameObject(new Vector3f(-67.8f, 23.0f, -148.7f), new Vector3f(-11.9f, 153.3f, 0))
                .addComponent(
                        new MovementComponent()
                )
                .addComponent(
                        new RenderComponent("sphere")
                )
                .addComponent(
                        new ControllerKeyboardMouse(
                                new float[]{0.3f, 0.23f, 0.23f, 0.23f, 0.23f, 0.23f, 0.23f, 11},
                                true
                        )
                )
                .addComponent(
                        new Skybox("peaks")
                )
                .addComponent(
                        new Camera()
                )
                /*.addComponent(
                        new SpotLightSource(200, new Vector4f(255, 255, 255, 1), new Vector3f(), 25, 1)
                )*/
                /*.addComponent(
                        new GUIElement(new Vector2f(Display.getWidth() / 2 - 32, Display.getHeight() / 2 - 32), new Vector2f(64, 64), "reticule")
                                .addComponent(new GUIQuad())
                )
                .addComponent(
                        new GUIElement(new Vector2f(5, 5), new Vector2f()).addComponent(new FPSTextComponent("Current FPS", 15))
                )
                .addComponent(
                        new GUIElement(new Vector2f(5, 25), new Vector2f()).addComponent(new TPSTextComponent("Current TPS", 15))
                )
                .addComponent(
                        new GUIElement(new Vector2f(5, 45), new Vector2f()).addComponent(new RAMTextComponent("Current RAM", 15))
                )
                .addComponent(
                        new GUIElement(new Vector2f(5, 85), new Vector2f()).addComponent(new PositionXTextComponent("x Position", 15))
                )
                .addComponent(
                        new GUIElement(new Vector2f(5, 105), new Vector2f()).addComponent(new PositionYTextComponent("y Position", 15))
                )
                .addComponent(
                        new GUIElement(new Vector2f(5, 125), new Vector2f()).addComponent(new PositionZTextComponent("z Position", 15))
                )
                .addComponent(
                        new GUIElement(new Vector2f(5, 165), new Vector2f()).addComponent(new VertexCountTextComponent("vertices", 15))
                )
                .addComponent(
                        new GUIElement(new Vector2f(5, 185), new Vector2f()).addComponent(new FaceCountTextComponent("faces", 15))
                )
                .addComponent(
                        new GUIElement(new Vector2f(Display.getWidth() / 2 - 125, Display.getHeight() / 2 - 50), new Vector2f(250, 100), "graph").setGUIDepartment(0)
                                .addComponent(new GUIButton(
                                        new ButtonPressingMethod() {
                                            @Override
                                            public void onPressing() {
                                                unPauseGame();
                                            }
                                        }
                                ))
                                .addComponent(new GUIQuad())
                )*/
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(0, 80, 0), new Vector3f())
                .addComponent(
                        new RenderComponent("monkey")
                )
                .addComponent(
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
                        new RenderComponent("Sci-fi_Tropical_city")
                )
                .createAllComponents());

        addGameObject(new GameObject(new Vector3f(), new Vector3f(35, -45, 0))
                .addComponent(
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(0, 1, 0))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(0, -1, 0))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(1, 0, 0))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(-1, 0, 0))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(0, 0, 1))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        new GlobalLightSource(1, new Vector4f(255, 251, 237, 1), new Vector3f(0, 0, -1))
                                .setSpecularLighting(false)
                                .setDependent(false)
                                .setShadowThrowing(false)
                )
                .addComponent(
                        new GlobalLightSource(15, new Vector4f(255, 251, 237, 1), new Vector3f(0, -1, 0))
                )
                .createAllComponents());

        isLoading = false;

    }

    public static void pauseGame() {

        Mouse.setGrabbed(false);
        isGamePaused = true;

        int menuDepartment;

        if (maxMenuGUIDepartments > 0) menuDepartment = 0;

        else menuDepartment = -1;

        activeMenuGUIDepartment = activeGUIDepartment = menuDepartment;

    }

    public static void unPauseGame() {

        Mouse.setGrabbed(true);
        isGamePaused = false;
        activeGUIDepartment = -1;

    }

    public static void stopGame() {

        AudioHelper.killALData();
        ThreadHelper.stopAllThreads();
        System.exit(0);

    }

}
