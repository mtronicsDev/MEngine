package mEngine.util;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;

import static mEngine.util.ResourceHelper.*;

public class TextureHelper {

    public static Texture loadTexture(String fileName) {

        Texture texture = null;

        try {

            texture = TextureLoader.getTexture("PNG", new FileInputStream(getResource(fileName, RES_TEXTURE)));

        } catch(IOException e) {

            e.printStackTrace();
            System.exit(1);

        }

        return texture;

    }

}
