package mEngine.interactive.gameObjects.components;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.util.input.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Camera extends Component {

    public float zoom = 1.3f;

    public Camera() {

        //Sets perspective
        glMatrixMode(GL_PROJECTION);
        gluPerspective(45, (float)Display.getWidth() / Display.getHeight(), 0.1f, 1000);
        glViewport(0, 0, Display.getWidth(), Display.getHeight());

        glMatrixMode(GL_MODELVIEW);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }

    public  void onCreation(GameObject obj) {}

    public void onUpdate(GameObject obj) {

        GraphicsController.switchTo3D();
        if(Input.isKeyPressed(Keyboard.KEY_F)) zoom --;
        else if(Input.isKeyPressed(Keyboard.KEY_G)) zoom ++;

        Vector3f position = obj.position;
        Vector3f rotation = obj.rotation;

        glLoadIdentity();

        glRotatef(rotation.x, 1, 0, 0);
        glRotatef(rotation.y, 0, 1, 0);
        glRotatef(rotation.z, 0, 0, 1);

        glTranslatef(-position.x, -position.y - zoom, -position.z);

    }

    public void onRemoteUpdate(GameObject obj) {}

}