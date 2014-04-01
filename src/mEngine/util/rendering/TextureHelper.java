package mEngine.util.rendering;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static mEngine.util.ResourceHelper.RES_TEXTURE;
import static mEngine.util.ResourceHelper.getResource;

public class TextureHelper {

    private static Map<String, Texture> loadedTextures = new HashMap<String, Texture>();

    private static void loadTexture(String fileName) {

        try {

            Texture texture = TextureLoader.getTexture("PNG", new FileInputStream(getResource(fileName, RES_TEXTURE)));
            loadedTextures.put(fileName, texture);

        } catch (IOException e) {

            e.printStackTrace();
            System.exit(1);

        }

    }

    public static Texture getTexture(String name) {

        Texture texture = loadedTextures.get(name);

        if (loadedTextures.containsKey(name)) return texture;
        else {

            loadTexture(name);
            return getTexture(name);

        }

    }

    public static void removeTexture(String name) {
        loadedTextures.remove(name);
    }

}