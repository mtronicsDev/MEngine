package mEngine.util.preferences;

import mEngine.core.GameController;
import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;

import java.io.File;

public class Saver {

    public static void saveGameObject(GameObject obj, String fileName) {

        for(Component component : obj.components.values()) {

            saveComponent(component, fileName);

        }

    }

    public static void saveComponent(Component component, String fileName) {



    }

    public static void saveWorld(String fileName) {

        for(GameObject obj : ObjectController.gameObjects) {

            saveGameObject(obj, fileName);

        }

    }

    public static void saveCurrentWorld() {

        saveWorld(GameController.currentWorld);

    }

}
