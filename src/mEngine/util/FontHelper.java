package mEngine.util;

import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class FontHelper {

    public static TrueTypeFont loadFont(String face, int style, int size, boolean antiAliasing) {

        Font fontTemplate = new Font(face, style, size);
        return new TrueTypeFont(fontTemplate, antiAliasing);

    }

}
