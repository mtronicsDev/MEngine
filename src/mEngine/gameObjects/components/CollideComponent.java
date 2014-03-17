package mEngine.gameObjects.components;

import mEngine.gameObjects.GameObject;
import mEngine.physics.collisions.Collider;
import org.lwjgl.util.vector.Vector3f;

public class CollideComponent extends Component {

    public boolean destroyable;
    public boolean ableToCollide;
    public Vector3f modelSize;

    public CollideComponent(boolean destroyable, boolean ableToCollide) {

        this(destroyable, ableToCollide, null);

    }

    public CollideComponent(boolean destroyable, boolean ableToCollide, Vector3f modelSize) {

        this.destroyable = destroyable;
        this.ableToCollide = ableToCollide;
        this.modelSize = modelSize;

    }

    public void onCreation(GameObject obj) {
    }

    public void onUpdate(GameObject obj) {
    }

    public void onRemoteUpdate(GameObject obj) {

        MovementComponent movementComponent = (MovementComponent) obj.getComponent("movementComponent");

        if (movementComponent != null) movementComponent.movedSpace = Collider.getMovedSpace(obj);

    }

}
