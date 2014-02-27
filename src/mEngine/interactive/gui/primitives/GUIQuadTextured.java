package mEngine.interactive.gui.primitives;

import mEngine.util.TextureHelper;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class GUIQuadTextured extends GUIQuad {

    Texture texture;

    public GUIQuadTextured(Vector2f pos, Vector2f size, String fileName) {

        this(pos, new Vector2f(), size, fileName);

    }

    public GUIQuadTextured(Vector2f pos, Vector2f rot, Vector2f size, String fileName) {

        super(pos, rot, size);

        texture = TextureHelper.getTexture(fileName);

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
