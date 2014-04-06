package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.CollideComponent;
import mEngine.gameObjects.components.RenderComponent;
import mEngine.util.TimeHelper;
import mEngine.util.input.Input;
import mEngine.util.rendering.RenderHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class GameLoop implements Runnable {

    public void run() {

        while (!Display.isCreated()) {
        } //Waiting for Display creation

        while (!Display.isCloseRequested()) {

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

            TimeHelper.updateDeltaTime();

            for (int i = 0; i < ObjectController.gameObjects.size(); i++) {

                ObjectController.getGameObject(i).update();

            }

            TimeHelper.updateTPS();

        }

        GameController.stopGame();

    }

}
