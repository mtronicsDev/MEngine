package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.CollideComponent;
import mEngine.gameObjects.components.RenderComponent;
import mEngine.util.TimeHelper;
import mEngine.util.input.Input;
import mEngine.util.rendering.RenderHelper;
import mEngine.util.serialization.Serializer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class GameLoop implements Runnable {

    public void run() {

        while (!Display.isCreated()) {
        } //Waiting for Display creation

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            if (Input.isKeyDown(Keyboard.KEY_ESCAPE)) {

                if (GameController.isGamePaused) GameController.unPauseGame();
                else GameController.pauseGame();

            }

            if (Input.isKeyPressed(Keyboard.KEY_Y)) {

                ObjectController.addGameObject(
                        new GameObject(ObjectController.getGameObject(0).position, new Vector3f())
                                .addComponent("renderComponent", new RenderComponent("texturedStar"))
                                .addComponent("collideComponent", new CollideComponent(false, true))
                );

            }

            if (Input.isKeyDown(Keyboard.KEY_T)) {

                TimeHelper.isInSlowMotion = !TimeHelper.isInSlowMotion;

            }

            TimeHelper.updateDeltaTime();

            for (int i = 0; i < ObjectController.gameObjects.size(); i++) {

                if(!Serializer.isSerializing) ObjectController.getGameObject(i).update();

            }

            if(Input.isKeyDown(Keyboard.KEY_F9)) Serializer.serializeAll();
            if(Input.isKeyDown(Keyboard.KEY_F10)) Serializer.deSerializeAll();

            TimeHelper.updateTPS();

        }

        GameController.stopGame();

    }

}
