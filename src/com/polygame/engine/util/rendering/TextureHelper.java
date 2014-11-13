/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.util.rendering;

import com.polygame.engine.graphics.renderable.animations.TextureAnimation;
import com.polygame.engine.graphics.renderable.textures.AnimatedTexture;
import com.polygame.engine.graphics.renderable.textures.StaticTexture;
import com.polygame.engine.graphics.renderable.textures.Texture;
import com.polygame.engine.util.resources.ResourceHelper;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextureHelper {

    private static Map<String, Texture> loadedTextures = new HashMap<String, Texture>();

    /**
     * Loads a texture into the list.
     *
     * @param fileName The name of the texture.
     */
    private static void loadTexture(String fileName) {

        File file = ResourceHelper.getResource(fileName, ResourceHelper.RES_TEXTURE);

        if (file.exists()) {

            try {

                org.newdawn.slick.opengl.Texture textureData = TextureLoader.getTexture("PNG", new FileInputStream(ResourceHelper.getResource(fileName, ResourceHelper.RES_TEXTURE)));
                StaticTexture texture = new StaticTexture(textureData);
                loadedTextures.put(fileName, texture);

            } catch (IOException e) {

                e.printStackTrace();
                System.exit(1);

            }

        } else {

            TextureAnimation animation = null;

            try {
                animation = TextureAnimationHelper.getAnimation(fileName);
            } catch (IOException e) {

                e.printStackTrace();
                System.exit(1);

            }

            loadedTextures.put(fileName, new AnimatedTexture(animation));

        }

    }

    /**
     * Returns the desired texture.
     * The texture gets loaded if it isn't already.
     *
     * @param name The name of the texture.
     * @return The desired texture.
     */
    public static Texture getTexture(String name) {

        if (loadedTextures.containsKey(name)) return loadedTextures.get(name);
        else {

            loadTexture(name);
            return getTexture(name);

        }

    }

    /**
     * Unloads a texture. Use this for memory efficiency.
     *
     * @param name The name of the texture.
     */
    public static void removeTexture(String name) {
        loadedTextures.remove(name);
    }

}
