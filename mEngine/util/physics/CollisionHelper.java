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

    private static CollisionResponseMethod breakAtSpecificPointMethod = new CollisionResponseMethod() {
        @Override
        public void onCollision() {
        }
    };

    private static CollisionResponseMethod breakCompletelyMethod = new CollisionResponseMethod() {
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

    private static CollisionResponseMethod bounceMethod = new CollisionResponseMethod() {
        @Override
        public void onCollision() {

        }
    };

}
