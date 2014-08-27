package com.mGameLabs.mEngine.util.debug.texts;

import com.mGameLabs.mEngine.gameObjects.components.gui.guiComponents.GUIText;
import com.mGameLabs.mEngine.util.time.TimeHelper;

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
