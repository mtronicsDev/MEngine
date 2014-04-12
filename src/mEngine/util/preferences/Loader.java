package mEngine.util.preferences;

import mEngine.core.GameController;
import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.*;
import mEngine.gameObjects.components.controller.ControllerArtificialIntelligence;
import mEngine.gameObjects.components.controller.ControllerGamepad;
import mEngine.gameObjects.components.controller.ControllerKeyboardMouse;
import mEngine.util.ArrayHelper;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Loader {

    public static void clearAndLoadWorld(GameObject[] gameObjectsToKeep, String fileName) {

        for(GameObject obj : ObjectController.gameObjects) {

            boolean[] areGameObjectsTheSame = new boolean[gameObjectsToKeep.length];
            boolean[] booleansToCheck = ArrayHelper.fillArray(gameObjectsToKeep.length, false);

            for(int count = 0; count < gameObjectsToKeep.length; count++) {

                GameObject objToKeep = gameObjectsToKeep[count];

                if(objToKeep == obj) areGameObjectsTheSame[count] = true;

            }

            if(areGameObjectsTheSame.equals(booleansToCheck)) ObjectController.removeGameObject(ObjectController.gameObjects.indexOf(obj));

        }

        loadWorld(fileName);

        GameController.currentWorld = fileName;

    }

    public static void loadWorld(String fileName) {

        GameObject[] gameObjects = new GameObject[] {};

        try {

            FileInputStream fileInputStream = new FileInputStream(ResourceHelper.getResource(fileName, ResourceHelper.RES_WORLD));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            try {

                gameObjects = (GameObject[]) objectInputStream.readObject();

            }

            catch (ClassNotFoundException e) {

                e.printStackTrace();
                System.exit(1);

            }

        }

        catch (IOException e) {

            e.printStackTrace();
            System.exit(1);

        }

        for(GameObject obj : gameObjects) {

            ObjectController.addGameObject(obj);

        }

    }

}
