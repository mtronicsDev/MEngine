package mEngine.physics;

import mEngine.interactive.gameObjects.GameObjectRenderable;
import org.lwjgl.util.vector.Vector3f;

import mEngine.interactive.gameObjects.GameObject;

public class Collider {

       public static boolean areAABBsColliding(GameObjectRenderable a, GameObjectRenderable b) {

           Vector3f posA = a.position;
           Vector3f posB = b.position;

           Vector3f sizeA = a.model.getSize();
           Vector3f sizeB = b.model.getSize();

           boolean colliding = posA.x < posB.x + sizeB.x
                   && posA.x + sizeA.x > posB.x
                   && posA.y < posB.y + sizeB.y
                   && posA.y + sizeA.y > posB.y
                   && posA.z < posB.z + sizeB.z
                   && posA.z + sizeA.z > posB.z;

           return colliding;

       }

        public static Vector3f getMovedSpace(GameObjectRenderable a, GameObjectRenderable b) {

            Vector3f movedSpace = new Vector3f(0, 0, 0);



            return movedSpace;

        }

}
