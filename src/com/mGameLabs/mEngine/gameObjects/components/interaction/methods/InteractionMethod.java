package com.mGameLabs.mEngine.gameObjects.components.interaction.methods;

import com.mGameLabs.mEngine.gameObjects.GameObject;
import com.mGameLabs.mEngine.gameObjects.components.interaction.InteractionComponent;

public abstract class InteractionMethod {

    protected GameObject parent;
    protected InteractionComponent caller;

    public void setParent(GameObject object) {

        parent = object;

        caller = (InteractionComponent) parent.getAnyComponent(InteractionComponent.class);

    }

}
