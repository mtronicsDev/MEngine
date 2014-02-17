package mEngine.interactive.components;

import mEngine.interactive.gameObjects.GameObject;
import mEngine.physics.Collider;
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

    public void onCreation(GameObject obj) {}
    public void onUpdate(GameObject obj) {}

    public void onRemoteUpdate(GameObject obj) {

        MovementComponent movementComponent = (MovementComponent)obj.getComponent("movementComponent");

        if(movementComponent != null) movementComponent.movedSpace = Collider.getMovedSpace(obj);

    }

}
