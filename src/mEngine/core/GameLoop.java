package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.audio.AudioSource;
import mEngine.gameObjects.modules.renderable.Camera;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.graphics.GraphicsController;
import mEngine.util.input.Input;
import mEngine.util.serialization.Serializer;
import mEngine.util.time.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class GameLoop implements Runnable {

    public void run() {

        //noinspection StatementWithEmptyBody
        while (!Display.isCreated()) {
        } //Waiting for Display creation

        while (!Display.isCloseRequested() && !Thread.interrupted()) {

            if (!GameController.isLoading) {

                if (Input.isKeyDown(Keyboard.KEY_ESCAPE)) {

                    if (GameController.isGamePaused) GameController.unPauseGame();
                    else GameController.pauseGame();

                }

                if (Input.isKeyPressed(Keyboard.KEY_Y)) {

                    ObjectController.addGameObject(
                            new GameObject(ObjectController.getGameObject(0).position, new Vector3f())
                                    .addModule(new RenderModule("texturedStar"))
                                    .createModules()
                    );

                }

                if (Input.isKeyDown(Keyboard.KEY_T)) TimeHelper.updateTimeModifier();

                if (Input.isKeyDown(Keyboard.KEY_O)) {

                    GameObject object = ObjectController.gameObjects.get(0);

                    object.position = new Vector3f();
                    object.rotation = new Vector3f();
                    object.percentRotation = new Vector3f(0, 0, 1);

                    ((Camera) object.getModule(Camera.class)).zoom = 0;

                }

                if(Input.isKeyDown(Keyboard.KEY_1)) {
                    AudioSource source = (AudioSource) ObjectController.getGameObject(1).getModule(AudioSource.class);
                    source.play();
                }

                if(Input.isKeyDown(Keyboard.KEY_2)) {
                    AudioSource source = (AudioSource) ObjectController.getGameObject(1).getModule(AudioSource.class);
                    source.pause();
                }

                if(Input.isKeyDown(Keyboard.KEY_3)) {
                    AudioSource source = (AudioSource) ObjectController.getGameObject(1).getModule(AudioSource.class);
                    source.stop();
                }

                if(Input.isKeyDown(Keyboard.KEY_4)) {
                    AudioSource source = (AudioSource) ObjectController.getGameObject(1).getModule(AudioSource.class);
                    source.pitch -= 0.1f;
                }

                if(Input.isKeyDown(Keyboard.KEY_5)) {
                    AudioSource source = (AudioSource) ObjectController.getGameObject(1).getModule(AudioSource.class);
                    source.pitch += 0.1f;
                }

                if(Input.isKeyDown(Keyboard.KEY_6)) {
                    AudioSource source = (AudioSource) ObjectController.getGameObject(1).getModule(AudioSource.class);
                    source.gain -= 0.1f;
                }

                if(Input.isKeyDown(Keyboard.KEY_7)) {
                    AudioSource source = (AudioSource) ObjectController.getGameObject(1).getModule(AudioSource.class);
                    source.gain += 0.1f;
                }

                if (Input.isKeyDown(Keyboard.KEY_B))
                    GraphicsController.isBlackAndWhite = !GraphicsController.isBlackAndWhite;

            }

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
