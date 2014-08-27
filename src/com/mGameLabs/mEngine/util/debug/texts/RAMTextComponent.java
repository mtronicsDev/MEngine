package com.mGameLabs.mEngine.util.debug.texts;

import com.mGameLabs.mEngine.gameObjects.components.gui.guiComponents.GUIText;

import static com.mGameLabs.mEngine.util.debug.RuntimeHelper.*;
import static com.mGameLabs.mEngine.util.math.NumberFormatHelper.cutDecimals;

public class RAMTextComponent extends GUIText {

    public RAMTextComponent(String text, int fontSize) {

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
