/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.renderable;

import com.polygame.engine.graphics.Renderer;
import com.polygame.engine.util.math.vectors.Matrix3f;
import com.polygame.engine.util.math.vectors.VectorHelper;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Camera extends ModuleRenderable {

    public float zoom = 0;
    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation;

    public Camera() {

        position = new Vector3f();
        rotation = new Vector3f();
        percentRotation = new Vector3f();

    }

    public void onUpdate() {

        rotation = parent.rotation;
        percentRotation = parent.percentRotation;

        if (!(Float.isNaN(parent.position.x) || Float.isNaN(parent.position.y) || Float.isNaN(parent.position.z)))
            position = VectorHelper.sumVectors(new Vector3f[]{VectorHelper.multiplyVectorByFloat(new Vector3f(percentRotation.x, percentRotation.y, -percentRotation.z), -zoom),
              parent.position});

    }

    public void render() {

        glLoadIdentity();

        if (zoom == 0) {

            glRotatef(rotation.x, 1, 0, 0);
            glRotatef(rotation.y, 0, 1, 0);
            glRotatef(rotation.z, 0, 0, 1);

            glTranslatef(-position.x, -position.y, -position.z);

        } else {

            Vector3f up = new Vector3f(0, 1, 0);

            if (parent.rotation.z != 0) {

                float radiantRotation = (float) -Math.toRadians(parent.rotation.z);

                Matrix3f zAxisRotationMatrix = new Matrix3f(
                  new Vector3f((float) Math.cos(radiantRotation), (float) -Math.sin(radiantRotation), 0),
                  new Vector3f((float) Math.sin(radiantRotation), (float) Math.cos(radiantRotation), 0),
                  new Vector3f(0, 0, 1)
                );

                up = zAxisRotationMatrix.multiplyByVector(up);

            }

            float[] lookAtMatrix = new float[16];

            lookAtMatrix[ 0] = position.x;
            lookAtMatrix[ 1] = up.x;
            lookAtMatrix[ 2] = -position.x;
            lookAtMatrix[ 3] = 0;

            lookAtMatrix[ 4] = position.y;
            lookAtMatrix[ 5] = up.y;
            lookAtMatrix[ 6] = -position.y;
            lookAtMatrix[ 7] = 0;

            lookAtMatrix[ 8] = position.z;
            lookAtMatrix[ 9] = up.z;
            lookAtMatrix[10] = -position.z;
            lookAtMatrix[11] = 0;

            lookAtMatrix[12] = -(position.x);
            lookAtMatrix[13] = -(up.y);
            lookAtMatrix[14] = (position.z);
            lookAtMatrix[15] = 1;

            FloatBuffer lookAt = FloatBuffer.allocate(16);
            lookAt.put(lookAtMatrix);
            glMultMatrix(lookAt);
            //TODO: gluLookAt(position.x, position.y, position.z, parent.position.x, parent.position.y, parent.position.z, up.x, up.y, up.z);

        }

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.setCamera(this);

    }

}