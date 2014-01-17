package mEngine.interactive.controls;

import mEngine.interactive.gameObjects.GameObjectMovable;

public abstract class Controller {

    public Controller() {}

    //Controls used while playing
    public abstract void updateObject(GameObjectMovable obj);

}
