package mEngine.util.debug.texts;

import mEngine.gameObjects.components.gui.guiComponents.GUIText;
import mEngine.util.timing.TimeHelper;

public class TPSTextComponent extends GUIText {

    public TPSTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = String.valueOf("ticks per second: " + TimeHelper.TPS + " TPS");

    }

}
