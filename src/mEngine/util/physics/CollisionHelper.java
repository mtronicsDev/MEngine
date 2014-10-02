/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

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
