package mEngine.graphics.renderable.animations;

import org.newdawn.slick.opengl.Texture;

public class TextureAnimation extends Animation {

    public TextureAnimation(TextureKeyFrame[] keyFrames, boolean stopAfterOneCycle) {
        super(keyFrames, stopAfterOneCycle);
    }

    public Texture getCurrentTexture() {

        return getCurrentKeyFrame().getData();

    }

}
