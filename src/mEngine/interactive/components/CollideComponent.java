package mEngine.interactive.components;

import mEngine.interactive.gameObjects.GameObject;
import org.lwjgl.util.vector.Vector3f;

public class CollideComponent extends Component {

    public boolean destroyable;
    public Vector3f modelSize;

    public CollideComponent(boolean destroyable) {

        this.destroyable = destroyable;
        modelSize = null;

    }

    public CollideComponent(boolean destroyable, Vector3f modelSize) {

        this.destroyable = destroyable;
        this.modelSize = modelSize;

    }

    public void initialize(GameObject obj) {}

    public void update(GameObject obj) {}

    public void updateByComponent(GameObject obj) {



    }

}
