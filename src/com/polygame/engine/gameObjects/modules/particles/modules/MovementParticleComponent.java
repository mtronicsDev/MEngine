/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.particles.modules;

import com.polygame.engine.core.ObjectController;
import com.polygame.engine.gameObjects.GameObject;
import com.polygame.engine.gameObjects.modules.renderable.Camera;
import com.polygame.engine.graphics.Renderer;
import com.polygame.engine.util.math.vectors.VectorHelper;

import javax.vecmath.Vector3f;

public class MovementParticleComponent extends ParticleComponent {

    Vector3f rotationAbility;

    public MovementParticleComponent(Vector3f rotationAbility) {

        this.rotationAbility = rotationAbility;

    }

    public void onUpdate() {

        Camera camera = null;

        if (Renderer.currentRenderQueue.camera != null) camera = Renderer.currentRenderQueue.camera;

        else {

            for (GameObject object : ObjectController.gameObjects) {

                Camera objectCamera = (Camera) object.getModule(Camera.class);

                if (objectCamera != null) {

                    camera = objectCamera;
                    break;

                }

            }

        }

        if (camera != null) {

            Vector3f cameraPosition = camera.position;
            Vector3f idealNormal = VectorHelper.normalizeVector(VectorHelper.subtractVectors(cameraPosition, parent.position));

            parent.normal = idealNormal;

            parent.rotation = new Vector3f(
              VectorHelper.getAngle(new Vector3f(0, 0, 1), new Vector3f(0, parent.normal.y, parent.normal.z)),
              VectorHelper.getAngle(new Vector3f(0, 0, 1), new Vector3f(parent.normal.x, 0, parent.normal.z)),
              VectorHelper.getAngle(new Vector3f(0, 0, 1), new Vector3f(parent.normal.x, parent.normal.y, 0))
            );


        }

    }

}
