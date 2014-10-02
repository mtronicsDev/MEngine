/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.physics.forces;

import org.lwjgl.util.vector.Vector3f;

import java.io.Serializable;

public class Force implements Serializable {

    public Vector3f direction;
    public boolean enabled;

    public Force(Vector3f direction) {

        this.direction = direction;
        enabled = false;

    }

    public Vector3f getDirectionalForceIfEnabled() {

        if (enabled) return direction;

        else return new Vector3f();

    }

}
