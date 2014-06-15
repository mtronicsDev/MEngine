package mEngine.gameObjects.components.physics;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.physics.collisions.Collider;
import mEngine.physics.collisions.primitives.Triangle;

import java.util.ArrayList;
import java.util.List;

public class PhysicComponent extends Component {

    public boolean ableToCollide = true;
    private int id;
    private static int physicObjectCount = 0;
    private static List<GameObject> objects = new ArrayList<GameObject>();
    public List<Triangle> collidingTriangles = new ArrayList<Triangle>();

    public PhysicComponent() {

        id = physicObjectCount;
        physicObjectCount++;

    }

    public void onUpdate() {

        if (id  == physicObjectCount - 1) Collider.collideObjects();

    }

    public static List<GameObject> getPhysicObjects() {

        return objects;

    }

}
