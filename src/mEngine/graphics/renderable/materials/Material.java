package mEngine.graphics.renderable.materials;

import mEngine.graphics.renderable.textures.Texture;
import mEngine.util.rendering.TextureHelper;
import org.newdawn.slick.Color;

import java.io.Serializable;

public abstract class Material implements Serializable {

    public Texture texture;
    public String textureName;
    public Color color = new Color(Color.white);

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public void setTextureFromName() {
        texture = TextureHelper.getTexture(textureName);
    }

    public void deleteTexture() {
        texture = null;
    }

    public boolean hasTexture() {
        return textureName != null;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void deleteColor() {
        setColor(null);
    }

    public void bind() {
        if (texture != null) texture.getTexture().bind();
        if (color != null) color.bind();
    }

}
