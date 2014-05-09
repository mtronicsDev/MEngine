package mEngine.graphics.renderable.materials;

import mEngine.graphics.renderable.textures.Texture;
import mEngine.util.rendering.TextureHelper;
import org.newdawn.slick.Color;

public abstract class Material {

    protected Texture texture;
    protected Color color = Color.white;

    public Material() {
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(String textureName) {
        setTexture(textureName, false);
    }

    public void setTexture(String textureName, boolean isAnimated) {
        texture = TextureHelper.getTexture(textureName, isAnimated);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void bind() {
        if (texture != null) texture.getTexture().bind();
        if (color != null) color.bind();
    }

}
