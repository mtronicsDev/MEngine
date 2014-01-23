package mEngine.physics;

import mEngine.interactive.gameObjects.GameObject;
import mEngine.interactive.gameObjects.GameObjectMovable;
import mEngine.interactive.gameObjects.GameObjectRenderable;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Collider {

       public static boolean areAABBsColliding(GameObjectMovable a, GameObjectMovable b) {

           boolean colliding;

           if(a.model == null) colliding = false;

           else {

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

           }

           return colliding;

       }

        public static boolean isCollidingWithSomething(GameObjectMovable obj) {

            boolean colliding;

            if(obj.model == null) {

                colliding = false;

            } else {

                colliding = false;

            }

            return colliding;

        }

        public static Vector3f getMovedSpace(Vector3f velocity, GameObjectMovable obj) {

            if(obj.model == null) return velocity;

            else {

                return velocity;

            }

        }

}
