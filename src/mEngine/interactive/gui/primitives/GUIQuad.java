package mEngine.interactive.gui.primitives;

import mEngine.interactive.gui.GUIElement;
import mEngine.util.TextureHelper;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class GUIQuad extends GUIElement {

    public Vector2f size;
    Texture texture;

    public GUIQuad(Vector2f pos, Vector2f size) { this(pos, new Vector2f(), size); }

    public GUIQuad(Vector2f pos, Vector2f rot, Vector2f size) {

        super(pos, rot);
        this.size = size;

        texture = TextureHelper.getTexture("texturedStar");

    }

    public void update() {

        texture.bind();

        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(position.x, position.y + size.y);
        glTexCoord2f(1, 1);
        glVertex2f(position.x + size.x, position.y + size.y);
        glTexCoord2f(1, 0);
        glVertex2f(position.x + size.x, position.y);
        glTexCoord2f(0, 0);
        glVertex2f(position.x, position.y);
        glEnd();

    }
}
