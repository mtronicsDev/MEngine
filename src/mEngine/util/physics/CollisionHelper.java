package mEngine.util.physics;

import mEngine.physics.collisions.CollisionResponseMethod;

public class CollisionHelper {

    private static CollisionResponseMethod slideMethod = new CollisionResponseMethod() {
        @Override
        public void onCollision() {
        }
    };

    private static CollisionResponseMethod stickMethod = new CollisionResponseMethod() {
        @Override
        public void onCollision() {
        }
    };

    private static CollisionResponseMethod breakMethod = new CollisionResponseMethod() {
        @Override
        public void onCollision() {
        }
    };

    private static CollisionResponseMethod rollMethod = new CollisionResponseMethod() {
        @Override
        public void onCollision() {
        }
    };

    private static CollisionResponseMethod slideAndRollMethod = new CollisionResponseMethod() {
        @Override
        public void onCollision() {
        }
    };

    public static CollisionResponseMethod getCollisionResponseMethod(boolean breaking, boolean sliding, boolean rolling) {

        CollisionResponseMethod collisionResponseMethod;

        if (breaking) collisionResponseMethod = breakMethod;

        else {

            if (sliding) {

                if (rolling) collisionResponseMethod = slideAndRollMethod;

                else collisionResponseMethod = slideMethod;

            } else if (rolling) collisionResponseMethod = rollMethod;

            else collisionResponseMethod = stickMethod;

        }

        return  collisionResponseMethod;

    }

}
