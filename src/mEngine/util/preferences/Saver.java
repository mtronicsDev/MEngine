package mEngine.util.preferences;

import mEngine.core.GameController;
import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Saver {

    public static void saveWorld(String fileName) {

        try {

            File file = ResourceHelper.getResource(fileName, ResourceHelper.RES_WORLD);

            if(!file.exists()) file.createNewFile();

            GameObject[] gameObjects = ObjectController.gameObjects.toArray(new GameObject[ObjectController.gameObjects.size()]);

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(gameObjects);

            objectOutputStream.close();
            fileOutputStream.close();

        }

        catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void saveCurrentWorld() {

        saveWorld(GameController.currentWorld);

    }

}
