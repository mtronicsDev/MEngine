package mEngine.interactive.gameObjects.components;

import mEngine.interactive.gameObjects.GameObject;

public class InteractionComponent extends Component {

    public boolean interactable;
    public int interactionKey;
    public boolean automaticallyInteractable;

    public InteractionComponent(boolean interactable) {

        this.interactable = interactable;

        automaticallyInteractable = true;

    }

    public InteractionComponent(boolean interactable, int interactionKey) {

        this.interactable = interactable;
        this.interactionKey = interactionKey;

        automaticallyInteractable = false;

    }

    public void onCreation(GameObject obj) {}

    public void onUpdate(GameObject obj) {}

}
