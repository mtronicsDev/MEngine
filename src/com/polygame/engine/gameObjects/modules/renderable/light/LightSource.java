/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.renderable.light;

import com.polygame.engine.gameObjects.GameObject;
import com.polygame.engine.gameObjects.modules.renderable.ModuleRenderable;
import com.polygame.engine.graphics.Renderer;
import com.polygame.engine.util.data.DataTypeHelper;
import com.polygame.engine.util.math.vectors.VectorHelper;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public abstract class LightSource extends ModuleRenderable {

    public float strength;
    public Vector3f position;
    public Vector4f color;
    public int specularLighting = 1;
    public Vector3f direction;
    public boolean dependent = true;
    public int shadowThrowing = 1;

    public LightSource(float strength, Vector4f color, Vector3f direction) {

        this.strength = strength;
        Vector3f colorIntensity = VectorHelper.divideVectorByFloat(new Vector3f(color.x, color.y, color.z), 255f);
        this.color = new Vector4f(colorIntensity.x, colorIntensity.y, colorIntensity.z, color.w);
        this.direction = direction;

    }

    public LightSource setDependent(boolean dependent) {

        this.dependent = dependent;
        return this;

    }

    public LightSource setSpecularLighting(boolean specularLighting) {

        this.specularLighting = DataTypeHelper.booleanToInteger(specularLighting);
        return this;

    }

    public LightSource setShadowThrowing(boolean shadowThrowing) {

        this.shadowThrowing = DataTypeHelper.booleanToInteger(shadowThrowing);
        return this;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        position = parent.position;

    }

    public void onUpdate() {

        position = parent.position;

        if (dependent)
            direction = new Vector3f(parent.percentRotation.x, parent.percentRotation.y, -parent.percentRotation.z);

    }

    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addLightSource(this);

    }

}
