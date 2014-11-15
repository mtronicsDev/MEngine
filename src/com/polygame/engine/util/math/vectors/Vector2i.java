/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.util.math.vectors;

import javax.vecmath.Tuple2i;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 15.11.2014 17:15
 */
public class Vector2i extends Tuple2i {

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(Vector2i reference) {
        x = reference.x;
        y = reference.y;
    }

}
