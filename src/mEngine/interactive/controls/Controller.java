package mEngine.interactive.controls;

import mEngine.interactive.gameObjects.GameObjectMovable;

public abstract class Controller {

    public boolean sneakModeToggle;
    public boolean continuouslyJumping;

    public Controller() {}

    //Controls used while playing
    public abstract void updateObject(GameObjectMovable obj);

}
