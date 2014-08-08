package mEngine.physics.collisions;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.physics.MovementComponent;
import mEngine.gameObjects.components.physics.PhysicComponent;
import mEngine.gameObjects.components.renderable.RenderComponent;
import mEngine.graphics.renderable.models.Face;
import mEngine.physics.collisions.primitives.Box;
import mEngine.physics.collisions.primitives.Plane;
import mEngine.physics.collisions.primitives.Triangle;
import mEngine.util.math.vectors.Matrix3f;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Collider {

    public static boolean areBoxesColliding(Box boxA, Box boxB) {

        return boxA.position.x < boxB.position.x + boxB.size.x
                && boxA.position.x + boxA.size.x > boxB.position.x
                && boxA.position.y < boxB.position.y + boxB.size.y
                && boxA.position.y + boxA.size.y > boxB.position.y
                && boxA.position.z < boxB.position.z + boxB.size.z
                && boxA.position.z + boxA.size.z > boxB.position.z;

    }

    public static void collideObjects() {



    }

}
