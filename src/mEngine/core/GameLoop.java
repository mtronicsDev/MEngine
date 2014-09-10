package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.util.input.Input;
import mEngine.util.serialization.Serializer;
import mEngine.util.time.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class GameLoop implements Runnable {

    public void run() {

        //noinspection StatementWithEmptyBody
        while (!Display.isCreated()) {
        } //Waiting for Display creation

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            TimeHelper.updateDeltaTime();
            if (ObjectController.getLoadingScreen() != null) ObjectController.getLoadingScreen().update();

            if (!GameController.isLoading) {

                for (GameObject gameObject : ObjectController.gameObjects) {

                    if (!Serializer.isSerializing) gameObject.update();

                }

                if (Input.isKeyDown(Keyboard.KEY_F9)) Serializer.serialize();
                if (Input.isKeyDown(Keyboard.KEY_F10)) Serializer.deSerializeLatest();

            }

            TimeHelper.updateTPS();

        }

        GameController.stopGame();

    }

}
