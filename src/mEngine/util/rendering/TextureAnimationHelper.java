package mEngine.util.rendering;

import mEngine.graphics.renderable.animations.TextureKeyFrame;
import mEngine.util.resources.ResourceHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextureAnimationHelper {

    public static TextureKeyFrame[] getFrames(String animationName) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(ResourceHelper.getResource(animationName, ResourceHelper.RES_TEXTURE_ANIMATED)));
        List<TextureKeyFrame> frames = new ArrayList<TextureKeyFrame>();
        String line;

        while ((line = reader.readLine()) != null) {

            if (line.startsWith("f ")) {

                int frameDelay;
                String frameName = line.split(" ")[1];
                if (line.split(" ")[2] != null) frameDelay = Integer.parseInt(line.split(" ")[2]);
                else frameDelay = 20;

                frames.add(new TextureKeyFrame(frameDelay, TextureHelper.getTexture(frameName).getTexture()));

            }

        }

        return frames.toArray(new TextureKeyFrame[frames.size()]);

    }

}
