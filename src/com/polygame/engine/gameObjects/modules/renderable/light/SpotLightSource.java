/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.renderable.light;

import com.polygame.engine.util.math.MathHelper;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class SpotLightSource extends LightSource {

    public float angle;
    public float transition;

    public SpotLightSource(float strength) {

        this(strength, new Vector4f(255, 255, 255, 1), new Vector3f(0, -1, 0), -1);

    }

    public SpotLightSource(float strength, Vector4f color) {

        this(strength, color, new Vector3f(0, -1, 0), -1);

    }

    public SpotLightSource(float strength, Vector3f direction, float angle) {

        this(strength, new Vector4f(255, 255, 255, 1), direction, angle);

    }

    public SpotLightSource(float strength, Vector4f color, Vector3f direction, float angle) {

        this(strength, color, direction, angle, 0);

    }

    public SpotLightSource(float strength, Vector4f color, Vector3f direction, float angle, float transition) {

        super(strength, color, direction);

        if (angle == -1) this.angle = -1;

        else this.angle = (float) Math.toRadians(angle);

        this.transition = (float) MathHelper.clamp(0, 1, transition);

    }

}
