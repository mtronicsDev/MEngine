package mEngine.gameObjects.components;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.GraphicsController;
import mEngine.util.input.Input;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Camera extends Component {

    public float zoom = 1.3f;
    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation;

    public Camera() {

        //Sets perspective
        glMatrixMode(GL_PROJECTION);
        gluPerspective(45, (float) Display.getWidth() / Display.getHeight(), 0.1f, 1000);
        glViewport(0, 0, Display.getWidth(), Display.getHeight());

        glMatrixMode(GL_MODELVIEW);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }

    public void onUpdate() {

        GraphicsController.switchTo3D();
        if (Input.isKeyPressed(Keyboard.KEY_F)) zoom--;
        else if (Input.isKeyPressed(Keyboard.KEY_G)) zoom++;

        position = VectorHelper.sumVectors(new Vector3f[]{parent.position, new Vector3f(0, zoom, 0)});
        rotation = parent.rotation;
        percentRotation = parent.percentRotation;

        glLoadIdentity();

        glRotatef(rotation.x, 1, 0, 0);
        glRotatef(rotation.y, 0, 1, 0);
        glRotatef(rotation.z, 0, 0, 1);

        glTranslatef(-position.x, -position.y, -position.z);

    }

    public void onRemoteUpdate(GameObject obj) {
    }

}