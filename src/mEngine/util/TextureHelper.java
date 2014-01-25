package mEngine.util;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TextureHelper {

    public static Texture loadTexture(File textureFile) {

        Texture texture = null;

        try {

            texture = TextureLoader.getTexture("PNG", new FileInputStream(textureFile));

        } catch(IOException e) {

            e.printStackTrace();
            System.exit(1);

        }

        return texture;

    }

}
