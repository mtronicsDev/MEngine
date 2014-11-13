/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.renderable.light;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class GlobalLightSource extends LightSource {

    public GlobalLightSource(float strength, Vector3f direction) {

        this(strength, new Vector4f(255, 255, 255, 1), direction);

    }

    public GlobalLightSource(float strength, Vector4f color, Vector3f direction) {

        super(strength, color, direction);

    }

}
