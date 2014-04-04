package mEngine.gameObjects.components;

import mEngine.util.input.Input;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Camera extends Component {

    public float zoom = 1.3f;
    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation;

    public Camera() {
    }

    public void onUpdate() {

        if (Input.isKeyPressed(Keyboard.KEY_F)) zoom--;
        else if (Input.isKeyPressed(Keyboard.KEY_G)) zoom++;

        if(!(Float.isNaN(parent.position.x) || Float.isNaN(parent.position.y) || Float.isNaN(parent.position.z)))
            position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, zoom, 0)});
        rotation = parent.rotation;
        percentRotation = parent.percentRotation;

    }

    public void render() {

        glLoadIdentity();

        glRotatef(rotation.x, 1, 0, 0);
        glRotatef(rotation.y, 0, 1, 0);
        glRotatef(rotation.z, 0, 0, 1);

        glTranslatef(-position.x, -position.y, -position.z);

    }

}