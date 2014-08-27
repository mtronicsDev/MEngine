package com.mGameLabs.mEngine.graphics.renderable.materials;

import com.mGameLabs.mEngine.graphics.renderable.textures.Texture;
import com.mGameLabs.mEngine.util.rendering.TextureHelper;
import org.newdawn.slick.Color;

import java.io.Serializable;

public abstract class Material implements Serializable {

    public Texture texture;
    public String textureName;
    public Color color = new Color(Color.white);

    public Texture getTexture() {
        return texture;
    }

    public Material setTexture(Texture texture) {

        this.texture = texture;
        return this;

    }

    public Material setTextureName(String textureName) {

        this.textureName = textureName;
        return this;

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

    public boolean hasColor() {
        return color != null;
    }

    public Color getColor() {
        return color;
    }

    public Material setColor(Color color) {

        this.color = new Color(color);
        return this;

    }

    public void deleteColor() {
        setColor(null);
    }

    public void bind() {

        if (texture != null) texture.getTexture().bind();

        else if (color != null) color.bind();

    }

}
