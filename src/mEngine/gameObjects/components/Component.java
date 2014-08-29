package mEngine.gameObjects.components;

import mEngine.gameObjects.GameObject;

import java.io.Serializable;

public abstract class Component implements Serializable {

    public GameObject parent;

    public void onCreation(GameObject obj) {
        parent = obj;
    }

    public void onUpdate() {
    }

    public void onDestroy() {
    }

    public void onRemoteUpdate() {
    }

    public void onSave() {
    }

    public void onLoad() {
    }

}
