package mEngine.graphics.renderable;

import mEngine.util.rendering.TextureHelper;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public abstract class Material {

    private Texture texture;
    private Color color;
    private float opacity;

    public Material() {
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(String textureName) {
        texture = TextureHelper.getTexture(textureName);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }
}
