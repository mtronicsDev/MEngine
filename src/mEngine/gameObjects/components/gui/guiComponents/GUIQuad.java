package mEngine.gameObjects.components.gui.guiComponents;

import mEngine.util.rendering.TextureHelper;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class GUIQuad extends GUIComponent {

    protected String textureName;
    protected Texture texture;

    public GUIQuad(String fileName) {

        textureName = fileName;

    }

    public void onUpdate() {

        super.onUpdate();

        if (texture == null) texture = TextureHelper.getTexture(textureName);
        texture.bind();

        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(parent.position.x, parent.position.y + parent.size.y);
        glTexCoord2f(1, 1);
        glVertex2f(parent.position.x + parent.size.x, parent.position.y + parent.size.y);
        glTexCoord2f(1, 0);
        glVertex2f(parent.position.x + parent.size.x, parent.position.y);
        glTexCoord2f(0, 0);
        glVertex2f(parent.position.x, parent.position.y);
        glEnd();

    }

    @Override
    public void onSave() {

        super.onSave();
        texture = null;

    }
}