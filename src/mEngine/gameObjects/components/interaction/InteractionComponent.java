package mEngine.gameObjects.components.interaction;

import mEngine.gameObjects.components.Component;

public class InteractionComponent extends Component {

    public boolean interactable;
    public Integer interactionKey;
    public String interactionDescription;

    public Interaction interaction;

    public InteractionComponent(boolean interactable, Interaction interaction) {

        this.interactable = interactable;
        this.interaction = interaction;

    }

    public InteractionComponent(boolean interactable, int interactionKey, Interaction interaction) {

        this.interactable = interactable;
        this.interactionKey = interactionKey;
        this.interaction = interaction;

    }

    public InteractionComponent(boolean interactable, int interactionKey, String interactionDescription, Interaction interaction) {

        this(interactable, interactionKey, interaction);

        this.interactionDescription = interactionDescription;

    }

    public void onUpdate() {

        //if (interacted) interaction.interact();

    }

}
