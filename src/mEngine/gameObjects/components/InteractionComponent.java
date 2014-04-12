package mEngine.gameObjects.components;

import java.io.Serializable;

public class InteractionComponent extends Component implements Serializable {

    public boolean interactable;
    public int interactionKey;
    public boolean automaticallyInteractable;
    public String interactionDescription;

    public InteractionComponent(boolean interactable) {

        this.interactable = interactable;

        automaticallyInteractable = true;

    }

    public InteractionComponent(boolean interactable, int interactionKey) {

        this.interactable = interactable;
        this.interactionKey = interactionKey;

        automaticallyInteractable = false;

    }

    public InteractionComponent(boolean interactable, int interactionKey, String interactionDescription) {

        this(interactable, interactionKey);

        this.interactionDescription = interactionDescription;

    }

    public void onUpdate() {
    }

}
