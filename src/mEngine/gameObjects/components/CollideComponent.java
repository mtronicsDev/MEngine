package mEngine.gameObjects.components;

import mEngine.physics.collisions.Collider;
import org.lwjgl.util.vector.Vector3f;

public class CollideComponent extends Component {

    public boolean destroyable;
    public boolean ableToCollide;
    public Vector3f modelSize;

    public CollideComponent(boolean destroyable, boolean ableToCollide) {

        this(destroyable, ableToCollide, null, false);

    }

    public CollideComponent(boolean destroyable, boolean ableToCollide, boolean addedAsLast) {

        this(destroyable, ableToCollide, null, addedAsLast);

    }

    public CollideComponent(boolean destroyable, boolean ableToCollide, Vector3f modelSize) {

        this(destroyable, ableToCollide, modelSize, false);

    }

    public CollideComponent(boolean destroyable, boolean ableToCollide, Vector3f modelSize, boolean addedAsLast) {

        super(addedAsLast);

        this.destroyable = destroyable;
        this.ableToCollide = ableToCollide;
        this.modelSize = modelSize;

    }

    public void onRemoteUpdate() {

        MovementComponent movementComponent = (MovementComponent) parent.getComponent("movementComponent");

        if (movementComponent != null) Collider.collideObject(parent);

    }

}
