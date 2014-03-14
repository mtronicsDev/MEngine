package mEngine.gameObjects.components;

import mEngine.gameObjects.GameObject;

public abstract class Component {

    public Component() {
    }

    public void onCreation(GameObject obj) {
    }

    public void onUpdate(GameObject obj) {
    }

    public void onDestroy(GameObject obj) {
    }

    public void onRemoteUpdate(GameObject obj) {
    }

}
