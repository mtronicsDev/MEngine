/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.physics.collisions.primitives;

import org.lwjgl.util.vector.Vector3f;

public class Box {

    public Vector3f position;
    public Vector3f size;

    public Box(Vector3f pos, Vector3f size) {

        position = pos;
        this.size = size;

    }

}
