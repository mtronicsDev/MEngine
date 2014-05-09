package mEngine.util.rendering;

import mEngine.graphics.renderable.animations.TextureAnimation;
import mEngine.graphics.renderable.textures.AnimatedTexture;
import mEngine.graphics.renderable.textures.StaticTexture;
import mEngine.graphics.renderable.textures.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static mEngine.util.resources.ResourceHelper.RES_TEXTURE;
import static mEngine.util.resources.ResourceHelper.getResource;

public class TextureHelper {

    private static Map<String, Texture> loadedTextures = new HashMap<String, Texture>();

    private static void loadTexture(String fileName, boolean isAnimated) {

        if (isAnimated) {

            TextureAnimation animation = null;

            try {
                animation = new TextureAnimation(TextureAnimationHelper.getFrames(fileName), false);
            } catch (IOException e) {

                e.printStackTrace();
                System.exit(1);

            }

            loadedTextures.put(fileName, new AnimatedTexture(animation));

        } else {

            try {

                org.newdawn.slick.opengl.Texture textureData = TextureLoader.getTexture("PNG", new FileInputStream(getResource(fileName, RES_TEXTURE)));
                StaticTexture texture = new StaticTexture(textureData);
                loadedTextures.put(fileName, texture);

            } catch (IOException e) {

                e.printStackTrace();
                System.exit(1);

            }

        }

    }

    public static Texture getTexture(String name) {

        return getTexture(name, false);

    }

    public static Texture getTexture(String name, boolean isAnimated) {

        if (loadedTextures.containsKey(name)) return loadedTextures.get(name);
        else {

            loadTexture(name, isAnimated);
            return getTexture(name);

        }

    }

    public static void removeTexture(String name) {
        loadedTextures.remove(name);
    }

}
