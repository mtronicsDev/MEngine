package mEngine.gameObjects.modules.interaction.methods;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.interaction.InteractionModule;

public abstract class InteractionMethod {

    protected GameObject parent;
    protected InteractionModule caller;

    public void setParent(GameObject object) {

        parent = object;

        caller = (InteractionModule) parent.getModule(InteractionModule.class);

    }

}
