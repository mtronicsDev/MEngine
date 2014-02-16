package mEngine.interactive.controls;

import mEngine.interactive.gameObjects.GameObject;

public abstract class Controller {

    public boolean sneakModeToggle;
    public boolean sprintModeToggle;
    public boolean continuouslyJumping;
    public float rotationSpeed;

    public Controller() {}

    public abstract void updateObject(GameObject obj);

}
