package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.rendering.RenderComponent;
import mEngine.graphics.GraphicsController;
import mEngine.util.TimeHelper;
import mEngine.util.input.Input;
import mEngine.util.serialization.Serializer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class GameLoop implements Runnable {

    public void run() {

        //noinspection StatementWithEmptyBody
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
                );

            }

            if (Input.isKeyDown(Keyboard.KEY_T)) TimeHelper.isInSlowMotion = !TimeHelper.isInSlowMotion;

            if (Input.isKeyDown(Keyboard.KEY_R))
                GraphicsController.isWireFrameMode = !GraphicsController.isWireFrameMode;

            TimeHelper.updateDeltaTime();

            for (int i = 0; i < ObjectController.gameObjects.size(); i++) {

                if (!Serializer.isSerializing) ObjectController.getGameObject(i).update();

            }

            if (Input.isKeyDown(Keyboard.KEY_F9)) Serializer.serialize();
            if (Input.isKeyDown(Keyboard.KEY_F10)) Serializer.deSerializeLatest();

            TimeHelper.updateTPS();

        }

        GameController.stopGame();

    }

}
