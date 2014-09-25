/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.physics.collisions;

import mEngine.physics.collisions.primitives.Box;

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
