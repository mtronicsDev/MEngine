package com.mGameLabs.mEngine.graphics.renderable.textures;

import com.mGameLabs.mEngine.graphics.renderable.animations.TextureAnimation;
import com.mGameLabs.mEngine.graphics.renderable.animations.TextureKeyFrame;

public class AnimatedTexture implements Texture {

    private TextureAnimation animation;

    public AnimatedTexture(TextureAnimation animation) {

        this.animation = animation;

    }

    public AnimatedTexture(Texture[] frames, boolean stopAfterOneCycle) {

        this(frames, 20, stopAfterOneCycle);

    }

    public AnimatedTexture(Texture[] frames, int delayBetweenFrames, boolean stopAfterOneCycle) {

        TextureKeyFrame[] keyFrames = new TextureKeyFrame[frames.length];
        for (int i = 0; i < frames.length; i++) {

            keyFrames[i] = new TextureKeyFrame(delayBetweenFrames, frames[i].getTexture());

        }

        animation = new TextureAnimation(keyFrames, stopAfterOneCycle);

    }

    @Override
    public org.newdawn.slick.opengl.Texture getTexture() {
        return animation.getCurrentTexture();
    }

    public TextureAnimation getAnimation() {
        return animation;
    }
}
