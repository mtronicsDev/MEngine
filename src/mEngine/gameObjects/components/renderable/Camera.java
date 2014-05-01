package mEngine.gameObjects.components.renderable;

import mEngine.graphics.Renderer;
import mEngine.util.time.TimeHelper;
import mEngine.util.input.Input;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class Camera extends ComponentRenderable {

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

        if (Input.isKeyPressed(Keyboard.KEY_F)) {

            if (zoom >= 0.015f * TimeHelper.deltaTime) zoom -= 0.015f * TimeHelper.deltaTime;

            else zoom = 0;

        }

        else if (Input.isKeyPressed(Keyboard.KEY_G)) zoom += 0.015f * TimeHelper.deltaTime;

        rotation = parent.rotation;
        percentRotation = parent.percentRotation;

        if (!(Float.isNaN(parent.position.x) || Float.isNaN(parent.position.y) || Float.isNaN(parent.position.z)))
            position = VectorHelper.sumVectors(new Vector3f[] {VectorHelper.multiplyVectorByFloat(new Vector3f(percentRotation.x, percentRotation.y, -percentRotation.z), -zoom),
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

            gluLookAt(position.x, position.y, position.z, parent.position.x, parent.position.y, parent.position.z, 0, 1, 0);

        }

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addCamera(this);

    }

}