package com.mGameLabs.mEngine.graphics.renderable.textures;

public class StaticTexture implements Texture {

    org.newdawn.slick.opengl.Texture texture;

    public StaticTexture(org.newdawn.slick.opengl.Texture texture) {

        this.texture = texture;

    }

    @Override
    public org.newdawn.slick.opengl.Texture getTexture() {
        return texture;
    }

}
