/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine;

import mEngine.core.GameController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.audio.AudioListener;
import mEngine.gameObjects.modules.controls.ControllerManual;
import mEngine.gameObjects.modules.gui.GUIElement;
import mEngine.gameObjects.modules.physics.MovementModule;
import mEngine.gameObjects.modules.physics.PhysicsModule;
import mEngine.gameObjects.modules.renderable.Camera;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.gameObjects.modules.renderable.Skybox;
import mEngine.gameObjects.modules.renderable.light.GlobalLightSource;
import mEngine.graphics.GraphicsController;
import mEngine.graphics.gui.GUIScreen;
import mEngine.graphics.gui.GUIScreenController;
import mEngine.graphics.renderable.LoadingScreen;
import mEngine.util.input.Input;
import mEngine.util.input.InputEventType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import static mEngine.core.GameController.*;
import static mEngine.core.ObjectController.setLoadingScreen;
import static mEngine.core.events.EventController.addEventHandler;

public class Main {

    /**
     * This is only for testing purposes.
     * @param args None.
     */
    public static void main(String[] args) {

        LoadingScreen standardLoadingScreen = new LoadingScreen("loadingScreen");
        setLoadingScreen(standardLoadingScreen);
        Mouse.setGrabbed(true);

        runGame();
        addEventHandler("gamePaused", () -> Mouse.setGrabbed(false));
        addEventHandler("gameResumed", () -> Mouse.setGrabbed(true));

        GUIScreen menuScreen = new GUIScreen("gamePaused", "gameResumed");
        GUIScreen inGame = new GUIScreen("gameResumed", "gamePaused", true);
        GUIScreen alwaysActive = new GUIScreen(true);
        GUIScreenController.addGUIScreen(menuScreen);
        GUIScreenController.addGUIScreen(inGame);
        GUIScreenController.addGUIScreen(alwaysActive);

        Input.assignInputEvent("pauseGame", true, InputEventType.ACTIVATED, Keyboard.KEY_ESCAPE);
        Input.assignInputEvent("screenshot", true, InputEventType.ACTIVATED, Keyboard.KEY_F2);

        //GameObject Time ;)
        new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0))
          .addModule(new MovementModule())
          .addModule(new RenderModule("sphere"))
          .addModule(
            new ControllerManual(
              new float[]{120, 100, 100, 100, 110, 110, 290}, //forward, backward, left, right, down, up, jump
              true //Can fly
            )
          )
          .addModule(new Skybox("devpun/devpun"))
          .addModule(new Camera())
          .addModule(new AudioListener())
          /*
          .addModule(new GUIElement(new Vector2f(5, 5)).addModule(new FPSTextModule(12)).setGUIScreen(inGame))
          .addModule(new GUIElement(new Vector2f(5, 19)).addModule(new TPSTextModule(12)).setGUIScreen(inGame))
          .addModule(new GUIElement(new Vector2f(5, 33)).addModule(new RAMTextModule(12)).setGUIScreen(inGame))
          .addModule(new GUIElement(new Vector2f(5, 47)).addModule(new VertexCountTextModule(12)).setGUIScreen(inGame))
          .addModule(new GUIElement(new Vector2f(5, 61)).addModule(new FaceCountTextModule(12)).setGUIScreen(inGame))
          */
          .addModule(
            new PhysicsModule(60, PhysicsModule.CollisionShape.SPHERE)
              .setDamping(.5f, .5f)
              .setMargin(.1f)
              .setInertia(new javax.vecmath.Vector3f(.2f, .2f, .2f))
              .setRestitution(.25f))
          .addModule(new GUIElement(new Vector2f()) {
              @Override
              public void onUpdate() {
                  super.onUpdate();
                  if (Input.inputEventTriggered("pauseGame")) {
                      if (isGamePaused()) resumeGame();
                      else pauseGame();
                  }
              }

              @Override
              public void render() {
                  super.render();
                  if (Input.inputEventTriggered("screenshot")) GraphicsController.takeScreenshot();
              }
          }.setGUIScreen(alwaysActive))
          /*.addModule(new GUIElement(new Vector2f(GraphicsController.getWidth() - 100, 50), new Vector2f(50, 50))
            .setGUIScreen(menuScreen)
            .addModule(new GUIButton()
              .setEventHandler(GUIButton.ButtonEvent.DOWN, GameController::stopGame))
            .addModule(new GUIQuad())
            .setMaterial((Material2D) new Material2D().setTextureName("gui/x")))*/
          .createModules();

        new GameObject(new Vector3f(0, 0, -3), new Vector3f())
          .addModule(new RenderModule("Dragon", true))
          .createModules();

        //Lights
        new GameObject(new Vector3f(), new Vector3f(35, 0, 0))
          .addModule(
            new GlobalLightSource(12f, new Vector4f(255, 251, 237, 1), new Vector3f(0, 1, 0))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false))
          .addModule(
            new GlobalLightSource(12f, new Vector4f(255, 251, 237, 1), new Vector3f(0, -1, 0))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false))
          .addModule(
            new GlobalLightSource(12f, new Vector4f(255, 251, 237, 1), new Vector3f(1, 0, 0))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false))
          .addModule(
            new GlobalLightSource(12f, new Vector4f(255, 251, 237, 1), new Vector3f(-1, 0, 0))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false))
          .addModule(
            new GlobalLightSource(12f, new Vector4f(255, 251, 237, 1), new Vector3f(0, 0, 1))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false))
          .addModule(
            new GlobalLightSource(12f, new Vector4f(255, 251, 237, 1), new Vector3f(0, 0, -1))
              .setSpecularLighting(false)
              .setDependent(false)
              .setShadowThrowing(false))
          .createModules();

        GameController.setLoading(false);

    }

}
