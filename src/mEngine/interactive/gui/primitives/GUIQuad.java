package mEngine.interactive.gui.primitives;

import mEngine.interactive.gui.GUIElement;
import mEngine.util.TextureHelper;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class GUIQuad extends GUIElement {

    public Vector2f size;

    public GUIQuad(Vector2f pos, Vector2f size) { this(pos, new Vector2f(), size); }

    public GUIQuad(Vector2f pos, Vector2f rot, Vector2f size) {

        super(pos, rot);
        this.size = size;

    }

    public void update() {

        glBegin(GL_QUADS);
        glVertex2f(position.x, position.y + size.y);
        glVertex2f(position.x + size.x, position.y + size.y);
        glVertex2f(position.x + size.x, position.y);
        glVertex2f(position.x, position.y);
        glEnd();

    }
}