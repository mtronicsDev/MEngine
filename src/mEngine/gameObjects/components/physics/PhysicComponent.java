package mEngine.gameObjects.components.physics;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.physics.collisions.Collider;

import java.util.ArrayList;
import java.util.List;

public class PhysicComponent extends Component {

    public boolean ableToCollide = true;
    public int id;
    private static int physicObjectCount = 0;
    private static List<GameObject> objects = new ArrayList<GameObject>();
    private static boolean neededToBeCleared = false;

    public PhysicComponent() {

        id = physicObjectCount;
        physicObjectCount++;

    }

    public void onUpdate() {

        if (neededToBeCleared) {

            physicObjectCount = objects.size();
            objects.clear();
            neededToBeCleared = false;

        }

        if (ableToCollide) {

            objects.add(parent);
            id = objects.size() - 1;

        }

        if (id  == physicObjectCount - 1) {

            Collider.collideObjects(objects);
            neededToBeCleared = true;

        }

    }

}
