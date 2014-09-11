package mEngine.util.debug.texts;

import mEngine.gameObjects.modules.gui.modules.GUIText;

import static mEngine.util.debug.RuntimeHelper.*;
import static mEngine.util.math.NumberFormatHelper.cutDecimals;

public class RAMTextModule extends GUIText {

    public RAMTextModule(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = String.valueOf("Memory usage: "
                + cutDecimals((float) getMemoryStats(USED_MEMORY) / getMemoryStats(TOTAL_MEMORY) * 100, 1)
                + "% [" + getMemoryStats(TOTAL_MEMORY) + " MB]");

    }

}
