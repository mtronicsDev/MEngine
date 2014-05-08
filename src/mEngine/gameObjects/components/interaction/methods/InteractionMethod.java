package mEngine.gameObjects.components.interaction.methods;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.interaction.InteractionComponent;

public abstract class InteractionMethod {

    protected GameObject parent;
    protected InteractionComponent caller;

    public void setParent(GameObject object) {

        parent = object;

        caller = ((InteractionComponent) parent.getComponent("interactionComponent"));

    }

}
