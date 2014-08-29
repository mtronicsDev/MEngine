package mEngine.graphics.renderable.animations;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;

public class TextureKeyFrame extends KeyFrame {

    public TextureKeyFrame(int delay, Texture texture) {
        super(delay, texture);
    }

    @Override
    public Texture getData() {
        return (TextureImpl) data;
    }

}
