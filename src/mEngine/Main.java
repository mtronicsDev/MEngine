/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine;

import mEngine.core.GameController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.audio.AudioListener;
import mEngine.gameObjects.modules.audio.AudioSource;
import mEngine.gameObjects.modules.controls.ControllerKeyboardMouse;
import mEngine.gameObjects.modules.interaction.AsyncInteraction;
import mEngine.gameObjects.modules.interaction.InteractionModule;
import mEngine.gameObjects.modules.physics.MovementModule;
import mEngine.gameObjects.modules.renderable.Camera;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.gameObjects.modules.renderable.light.GlobalLightSource;
import mEngine.graphics.renderable.LoadingScreen;
import mEngine.physics.forces.ForceController;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.threading.ThreadHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import static mEngine.core.GameController.runGame;
import static mEngine.core.ObjectController.addGameObject;
import static mEngine.core.ObjectController.setLoadingScreen;

public class Main {

    /**
     * This is only for testing purposes
     *
     * @param args None
     */
    public static void main(String[] args) {

        setLoadingScreen(new LoadingScreen("loadingScreen"));
        Mouse.setGrabbed(true);

        runGame();

        ForceController.addForce("gravity", new Vector3f(0, -9.81f, 0));

        //GameObject Time ;)
        addGameObject(new GameObject(new Vector3f(-67.8f, 23.0f, -148.7f), new Vector3f(-11.9f, 153.3f, 0))
                .addModule(
                        new MovementModule()
                )
                .addModule(
                        new RenderModule("sphere")
                )
                .addModule(
                        new ControllerKeyboardMouse(
                                new float[]{0.3f, 0.23f, 0.23f, 0.23f, 0.23f, 0.23f, 0.23f, 11},
                                true
                        )
                )
                /*.addModule(
                        new Skybox("peaks")
                )*/
                .addModule(
                        new Camera()
                )
                .addModule(
                        new AudioListener()
                )
                /*.addModule(
                        new SpotLightSource(200, new Vector4f(255, 255, 255, 1), new Vector3f(), 25, 1)
                )*/
                /*.addModule(
                        new GUIElement(new Vector2f(Display.getWidth() / 2 - 32, Display.getHeight() / 2 - 32), new Vector2f(64, 64), "reticule")
                                .addModule(new GUIQuad())
                )
                .addModule(
                        new GUIElement(new Vector2f(5, 5), new Vector2f()).addModule(new FPSTextModule("Current FPS", 15))
                )
                .addModule(
                        new GUIElement(new Vector2f(5, 25), new Vector2f()).addModule(new TPSTextModule("Current TPS", 15))
                )
                .addModule(
                        new GUIElement(new Vector2f(5, 45), new Vector2f()).addModule(new RAMTextModule("Current RAM", 15))
                )
                .addModule(
                        new GUIElement(new Vector2f(5, 85), new Vector2f()).addModule(new PositionXTextModule("x Position", 15))
                )
                .addModule(
                        new GUIElement(new Vector2f(5, 105), new Vector2f()).addModule(new PositionYTextModule("y Position", 15))
                )
                .addModule(
                        new GUIElement(new Vector2f(5, 125), new Vector2f()).addModule(new PositionZTextModule("z Position", 15))
                )
                .addModule(
                        new GUIElement(new Vector2f(5, 165), new Vector2f()).addModule(new VertexCountTextModule("vertices", 15))
                )
                .addModule(
                        new GUIElement(new Vector2f(5, 185), new Vector2f()).addModule(new FaceCountTextModule("faces", 15))
                )
                .addModule(
                        new GUIElement(new Vector2f(Display.getWidth() / 2 - 125, Display.getHeight() / 2 - 50), new Vector2f(250, 100), "graph").setGUIDepartment(0)
                                .addModule(new GUIButton(
                                        new ButtonPressAction() {
                                            @Override
                                            public void pressed() {
                                                unPauseGame();
                                            }
                                        }
                                ))
                                .addModule(new GUIQuad())
                )*/
                .createModules());

        addGameObject(new GameObject(new Vector3f(0, 80, 0), new Vector3f())
                .addModule(
                        new RenderModule("monkey")
                )
                .addModule(
                        new AudioSource("Unity", false, true)
                )
                .addModule(
                        new InteractionModule(true, 10, Keyboard.KEY_I, "move monkey", 25, new AsyncInteraction() {
                            @Override
                            public void interact() {

                                caller.enabled = false;

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

                                caller.enabled = true;

                            }
                        })
                )
                .createModules());

        addGameObject(new GameObject(new Vector3f(), new Vector3f())
                .addModule(
                        new RenderModule("Sci-fi_Tropical_city")
                )
                .createModules());

        addGameObject(new GameObject(new Vector3f(), new Vector3f(35, -45, 0))
                .addModule(
                        new GlobalLightSource(100, new Vector4f(180, 180, 0, 1), new Vector3f(0, 0, 1))
                                .setSpecularLighting(false)
                                        //.setDependent(false)
                                .setShadowThrowing(false)
                )
                .createModules());

        GameController.isLoading = false;

    }

}
