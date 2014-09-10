package mEngine.gameObjects.modules;

import mEngine.gameObjects.GameObject;

import java.io.Serializable;

public abstract class Module implements Serializable {

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
