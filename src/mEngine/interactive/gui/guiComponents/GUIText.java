package mEngine.interactive.gui.guiComponents;

import mEngine.util.FontHelper;
import mEngine.util.PreferenceHelper;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class GUIText extends GUIComponent {

    protected String text;
    protected Color color;
    protected TrueTypeFont font;

    public GUIText(String text, int fontSize) {

        this(text, Font.PLAIN, fontSize);

    }

    public GUIText(String text, int fontStyle, int fontSize) {

        this(text, PreferenceHelper.getValue("defaultFontFace"), fontStyle, fontSize);

    }

    public GUIText(String text, String fontFace, int fontStyle, int fontSize) {

        this(text, fontFace, fontStyle, fontSize, Color.white);

    }

    public GUIText(String text, String fontFace, int fontStyle, int fontSize, Color color) {

        font = FontHelper.loadFont(fontFace, fontStyle, fontSize, PreferenceHelper.getBoolean("antiAliasing"));
        this.text = text;
        this.color = color;

    }

    public void onUpdate() {

        super.onUpdate();
        font.drawString(parent.position.x, parent.position.y, text, color);

    }

    public void onExternalUpdate(Object[] args) {

        super.onExternalUpdate(args);
        if(args.length >= 1) this.text = (String)args[0];
        if(args.length == 2) onExternalUpdate((Integer)args[1]);
        if(args.length == 3) onExternalUpdate((Integer)args[1], (Integer)args[2]);
        if(args.length == 4) onExternalUpdate((Integer)args[1], (Integer)args[2], (String)args[3]);
        if(args.length == 5) onExternalUpdate((Integer)args[1], (Integer)args[2], (String)args[3], (Color)args[4]);

    }

    protected void onExternalUpdate(int fontSize) {

        onExternalUpdate(Font.PLAIN, fontSize);

    }

    protected void onExternalUpdate(int fontStyle, int fontSize) {

        onExternalUpdate( fontStyle, fontSize, PreferenceHelper.getValue("defaultFontFace"));

    }

    protected void onExternalUpdate(int fontStyle, int fontSize, String fontFace) {

        onExternalUpdate(fontStyle, fontSize, fontFace, Color.white);

    }

    protected void onExternalUpdate(int fontStyle, int fontSize, String fontFace, Color color) {

        font = FontHelper.loadFont(fontFace, fontStyle, fontSize, PreferenceHelper.getBoolean("antiAliasing"));
        this.color = color;

    }

}
