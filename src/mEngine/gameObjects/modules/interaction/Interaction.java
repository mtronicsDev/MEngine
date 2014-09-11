package mEngine.gameObjects.modules.interaction;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.interaction.InteractionModule;

abstract class Interaction {

    protected GameObject parent;
    protected InteractionModule caller;

    public void setParent(GameObject object) {

        parent = object;

        caller = (InteractionModule) parent.getModule(InteractionModule.class);

    }

}
