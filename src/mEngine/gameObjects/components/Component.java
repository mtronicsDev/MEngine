package mEngine.gameObjects.components;

import mEngine.gameObjects.GameObject;

public abstract class Component {

    protected GameObject parent;

    public void onCreation(GameObject obj) { parent = obj; }

    public void onUpdate() {}

    public void onDestroy() {}

    public void onRemoteUpdate() {}

}
