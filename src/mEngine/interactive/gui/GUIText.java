package mEngine.interactive.gui;

import mEngine.util.FontHelper;
import mEngine.util.PreferenceHelper;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class GUIText extends GUIElement {

    public String text;
    public Color color;
    private TrueTypeFont font;

    public GUIText(Vector2f pos, String text, int fontSize) {

        this(pos, new Vector2f(), text, Font.PLAIN, fontSize);

    }

    public GUIText(Vector2f pos, Vector2f rot, String text, int fontSize) {

        this(pos, rot, text, Font.PLAIN, fontSize);

    }

    public GUIText(Vector2f pos, Vector2f rot, String text, int fontStyle, int fontSize) {

        this(pos, rot, text, PreferenceHelper.getValue("defaultFontFace"), fontStyle, fontSize, Color.white);

    }

    public GUIText(Vector2f pos, Vector2f rot, String text, String fontFace, int fontStyle, int fontSize) {

        this(pos, rot, text, fontFace, fontStyle, fontSize, Color.white);

    }

    public GUIText(Vector2f pos, Vector2f rot, String text, String fontFace, int fontStyle, int fontSize, Color color) {

        super(pos, rot);
        font = FontHelper.loadFont(fontFace, fontStyle, fontSize, PreferenceHelper.getBoolean("antiAliasing"));
        this.text = text;
        this.color = color;

    }

    public void update() {

        font.drawString(position.x, position.y, text, color);

    }

}
