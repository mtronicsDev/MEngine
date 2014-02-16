package mEngine.interactive.components;

import mEngine.interactive.gameObjects.GameObject;
import mEngine.physics.forces.Force;

import java.util.ArrayList;
import java.util.List;

public abstract class Component {

    public  Component() {}

    public abstract void initialize(GameObject obj);

    public abstract void update(GameObject obj);

    public abstract void updateByComponent(GameObject obj);

}
