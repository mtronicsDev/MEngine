package mEngine.graphics.renderable.textures;

import mEngine.graphics.renderable.animations.TextureAnimation;

public class AnimatedTexture implements Texture {

    private TextureAnimation animation;

    public AnimatedTexture(TextureAnimation animation) {

        this.animation = animation;

    }

    @Override
    public org.newdawn.slick.opengl.Texture getTexture() {
        return animation.getCurrentTexture();
    }

    public TextureAnimation getAnimation() {
        return animation;
    }
}
