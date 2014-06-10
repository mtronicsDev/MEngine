package mEngine.gameObjects.components.interaction.methods;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.interaction.InteractionComponent;

public abstract class InteractionMethod {

    protected GameObject parent;
    protected InteractionComponent caller;

    public void setParent(GameObject object) {

        parent = object;

        for (Component component : parent.components) {

            if (component instanceof InteractionComponent) caller = (InteractionComponent) component;

        }

    }

}
