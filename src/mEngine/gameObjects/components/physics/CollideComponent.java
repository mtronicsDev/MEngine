package mEngine.gameObjects.components.physics;

import mEngine.gameObjects.components.Component;
import mEngine.physics.collisions.Collider;
import mEngine.physics.collisions.CollisionResponseMethod;
import org.lwjgl.util.vector.Vector3f;

public class CollideComponent extends Component {

    public boolean ableToCollide;
    public Vector3f modelSize;
    public CollisionResponseMethod collision;

    public CollideComponent(boolean ableToCollide, CollisionResponseMethod collision) {

        this(ableToCollide, null, collision);

    }

    public CollideComponent(boolean ableToCollide, Vector3f modelSize, CollisionResponseMethod collision) {

        this.ableToCollide = ableToCollide;
        this.modelSize = modelSize;
        this.collision = collision;

    }

    public void onRemoteUpdate() {

        Collider.collideObject(parent);

    }

}
