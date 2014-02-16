package mEngine.interactive.components;

import mEngine.interactive.gameObjects.GameObject;

public class InteractionComponent extends Component {

    public boolean interactable;

    public InteractionComponent(boolean interactable) {

        this.interactable = interactable;

    }

    public void initialize(GameObject obj) {}

    public void update(GameObject obj) {



    }

    public void updateByComponent(GameObject obj) {}

}
