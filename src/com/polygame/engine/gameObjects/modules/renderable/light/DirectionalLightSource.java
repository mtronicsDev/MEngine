/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.renderable.light;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class DirectionalLightSource extends LightSource {

    public float radius;

    public DirectionalLightSource(float strength, Vector3f direction) {

        this(strength, new Vector4f(255, 255, 255, 1), direction, -1);

    }

    public DirectionalLightSource(float strength, Vector3f direction, float radius) {

        this(strength, new Vector4f(255, 255, 255, 1), direction, radius);

    }

    public DirectionalLightSource(float strength, Vector4f color, Vector3f direction) {

        this(strength, color, direction, -1);

    }

    public DirectionalLightSource(float strength, Vector4f color, Vector3f direction, float radius) {

        super(strength, color, direction);

        this.radius = radius;

    }

}
