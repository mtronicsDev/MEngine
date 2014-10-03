/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine;

import mEngine.core.GameController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.audio.AudioListener;
import mEngine.gameObjects.modules.audio.AudioSource;
import mEngine.gameObjects.modules.controls.ControllerKeyboardMouse;
import mEngine.gameObjects.modules.physics.MovementModule;
import mEngine.gameObjects.modules.physics.PhysicsModule;
import mEngine.gameObjects.modules.renderable.Camera;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.gameObjects.modules.renderable.Skybox;
import mEngine.gameObjects.modules.renderable.light.GlobalLightSource;
import mEngine.graphics.renderable.LoadingScreen;
import mEngine.util.input.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import static mEngine.core.GameController.*;
import static mEngine.core.ObjectController.addGameObject;
import static mEngine.core.ObjectController.setLoadingScreen;
import static mEngine.core.events.EventController.addEventHandler;

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
        addEventHandler("gamePaused", () -> System.out.println("Game paused"));
        addEventHandler("gameResumed", () -> System.out.println("Game resumed"));

        Input.assignKey("pauseGame", Keyboard.KEY_ESCAPE);

        //GameObject Time ;)
        addGameObject(new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0))
          .addModule(
            new MovementModule()
          )
          .addModule(
            new RenderModule("sphere")
          )
          .addModule(
            new ControllerKeyboardMouse(
              new float[]{12, 10, 10, 10, 10, 10, 29}, //forward, backward, left, right, down, up, jump
              true //Can fly
            )
          )
          .addModule(
            new Skybox("peaks")
          )
          .addModule(
            new Camera()
          )
          .addModule(
            new AudioListener()
          )
          .addModule(
            new PhysicsModule(60, PhysicsModule.CollisionShape.SPHERE)
              .setDamping(.5f, .5f)
              .setMargin(.1f)
              .setInertia(new javax.vecmath.Vector3f(.2f, .2f, .2f))
              .setRestitution(.25f)
          )
          .addModule(new Module() {

              @Override
              public void onUpdate() {
                  super.onUpdate();
                  if (Input.isKeyDown("pauseGame")) {
                      if (isGamePaused) resumeGame();
                      else pauseGame();
                  }
              }
          })
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

        addGameObject(new GameObject(new Vector3f(0, 0, -20), new Vector3f())
          .addModule(
            new RenderModule("sphere")
          )
          .addModule(
            new PhysicsModule(10, PhysicsModule.CollisionShape.SPHERE)
              .setDamping(.5f, .5f)
              .setMargin(.1f)
              .setInertia(new javax.vecmath.Vector3f(.2f, .2f, .2f))
              .setRestitution(.25f)
          )
          .addModule(
            new AudioSource("Unity", false, true)
          )
          .createModules());

        addGameObject(new GameObject(new Vector3f(0, 0, -10), new Vector3f())
          .addModule(new RenderModule("soccerBall"))
          .addModule(new PhysicsModule(0.4f, PhysicsModule.CollisionShape.SPHERE)
            .setDamping(.25f, .25f)
            .setInertia(new javax.vecmath.Vector3f(.05f, .05f, .05f))
            .setMargin(.01f)
            .setRestitution(.02f))
          .createModules());

        /*addGameObject(new GameObject(new Vector3f(), new Vector3f())
                .addModule(
                        new RenderModule("Sci-fi_Tropical_city")
                )
                .createModules());*/

        addGameObject(new GameObject(new Vector3f(), new Vector3f())
          .addModule(
            new GlobalLightSource(50, new Vector4f(255, 255, 255, 1), new Vector3f(0, 0, 1))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false)
          )
          .addModule(
            new GlobalLightSource(50, new Vector4f(255, 255, 255, 1), new Vector3f(0, 0, -1))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false)
          )
          .addModule(
            new GlobalLightSource(50, new Vector4f(255, 255, 255, 1), new Vector3f(0, 1, 0))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false)
          )
          .addModule(
            new GlobalLightSource(50, new Vector4f(255, 255, 255, 1), new Vector3f(0, -1, 0))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false)
          )
          .addModule(
            new GlobalLightSource(50, new Vector4f(255, 255, 255, 1), new Vector3f(1, 0, 0))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false)
          )
          .addModule(
            new GlobalLightSource(50, new Vector4f(255, 255, 255, 1), new Vector3f(-1, 0, 0))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false)
          )
          .createModules());

        GameController.isLoading = false;

    }

}
