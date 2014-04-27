package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.components.Component;

public abstract class ComponentRenderable extends Component {

    public ComponentRenderable() {

        super(false);

    }

    public ComponentRenderable(boolean addedAsLast) {

        super(addedAsLast);

    }

    public abstract void addToRenderQueue();

}
