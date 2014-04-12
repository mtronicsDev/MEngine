package mEngine.util.serialization;

import mEngine.gameObjects.GameObject;

import java.io.Serializable;
import java.util.*;

public class SaveObject implements Serializable {

    java.util.List<GameObject> gameObjects = new ArrayList<GameObject>();

    public SaveObject(List<GameObject> objects) {

        gameObjects = objects;

    }

}
