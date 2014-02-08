package mEngine.interactive.gui.primitives;

import mEngine.interactive.gui.GUIElement;
import org.lwjgl.util.vector.Vector2f;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;

public class GUIEllipse extends GUIElement {
    Vector2f radius;

    public GUIEllipse(Vector2f pos, Vector2f radius) {

        this(pos, new Vector2f(), radius);

    }

    public GUIEllipse(Vector2f pos, Vector2f rot, Vector2f radius) {

        super(pos, rot);
        this.radius = radius;

    }

    public void update() {

        glBegin(GL_TRIANGLE_FAN);

        glVertex2f(position.x, position.y);

        for(int i = 360; i > 0; i--) {

            float radians = (float)Math.toRadians(i);

            glVertex2f(
                    position.x + ((float)cos(radians) * radius.x),
                    position.y + ((float)sin(radians) * radius.y)
            );

        }

        glEnd();

    }

}
