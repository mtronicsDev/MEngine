package mEngine.util.data;

import org.lwjgl.util.vector.Vector4f;

import java.awt.*;

public class ColorHelper {

    public static Color getHexColor(String hex) {
        if(hex.startsWith("#")) hex = hex.replace("#", "");

        //Conversion to long notation
        if(hex.length() == 3) {
            char r = hex.charAt(0);
            char g = hex.charAt(1);
            char b = hex.charAt(2);

            hex = new String(new char[]{r,r,g,g,b,b});
        }

        //Conversion to int
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return new Color(r, g, b);

    }

    public static Vector4f colorToRgba(Color color) {
        return new Vector4f(color.getRed() / 256,
                color.getGreen() / 256,
                color.getBlue() / 256,
                color.getAlpha() / 256);
    }

}
