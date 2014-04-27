package mEngine.gameObjects.components;

import sun.net.www.content.audio.basic;

public class InteractionComponent extends Component {

    public boolean interactable;
    public int interactionKey;
    public boolean automaticallyInteractable;
    public String interactionDescription;

    public InteractionComponent(boolean interactable) {

        this(interactable, false);

    }

    public InteractionComponent(boolean interactable, boolean addedAsLast) {

        super(addedAsLast);

        this.interactable = interactable;

        automaticallyInteractable = true;

    }

    public InteractionComponent(boolean interactable, int interactionKey) {

        this(interactable, interactionKey, false);

    }

    public InteractionComponent(boolean interactable, int interactionKey, boolean addedAsLast) {

        super(addedAsLast);

        this.interactable = interactable;
        this.interactionKey = interactionKey;

        automaticallyInteractable = false;

    }

    public InteractionComponent(boolean interactable, int interactionKey, String interactionDescription) {

        this(interactable, interactionKey, false);

        this.interactionDescription = interactionDescription;

    }

    public InteractionComponent(boolean interactable, int interactionKey, String interactionDescription, boolean addedAsLast) {

        this(interactable, interactionKey, addedAsLast);

        this.interactionDescription = interactionDescription;

    }

    public void onUpdate() {
    }

}
