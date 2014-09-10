package mEngine.util.debug.texts;

import mEngine.gameObjects.modules.gui.modules.GUIText;
import mEngine.util.time.TimeHelper;

public class FPSTextComponent extends GUIText {

    public FPSTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = String.valueOf("Frames per second: " + TimeHelper.FPS + " FPS");

    }

}
