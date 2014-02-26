package mEngine.interactive.gameObjects.components;

import mEngine.interactive.gameObjects.GameObject;

public class InteractionComponent extends Component {

    public boolean interactable;

    public InteractionComponent(boolean interactable) {

        this.interactable = interactable;

    }

    public void onCreation(GameObject obj) {}

    public void onUpdate(GameObject obj) {}

}
