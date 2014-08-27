package com.mGameLabs.mEngine.util.serialization;

import com.mGameLabs.mEngine.gameObjects.GameObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveObject implements Serializable {

    List<GameObject> gameObjects = new ArrayList<GameObject>();

    public SaveObject(List<GameObject> objects) {

        gameObjects = objects;

    }

}
