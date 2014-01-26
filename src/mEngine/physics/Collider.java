package mEngine.physics;

import mEngine.interactive.gameObjects.GameObjectMovable;
import org.lwjgl.util.vector.Vector3f;

public class Collider {

       public static boolean areAABBsColliding(GameObjectMovable a, GameObjectMovable b) {

           boolean colliding;

               Vector3f posA = a.position;
               Vector3f posB = b.position;

               Vector3f sizeA = a.model.getSize();
               Vector3f sizeB = b.model.getSize();

               colliding = posA.x < posB.x + sizeB.x
                       && posA.x + sizeA.x > posB.x
                       && posA.y < posB.y + sizeB.y
                       && posA.y + sizeA.y > posB.y
                       && posA.z < posB.z + sizeB.z
                       && posA.z + sizeA.z > posB.z;

           return colliding;

       }

        public static boolean isCollidingWithSomething(GameObjectMovable obj) {

            boolean colliding;

            colliding = false;

            return colliding;

        }

        public static Vector3f getMovedSpace(Vector3f velocity, GameObjectMovable obj) {

            Vector3f movedSpace;

            movedSpace = velocity;

            return  movedSpace;

        }

}
