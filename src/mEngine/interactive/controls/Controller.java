package mEngine.interactive.controls;

import mEngine.interactive.gameObjects.GameObject;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Controller {

    public Controller() {}

    //Controls used while playing
    public abstract void checkInputKeys(GameObject obj);

    //Artificial Intelligence
    public abstract void actIntelligently(GameObject obj);

}
