package mEngine.util.debug;

import mEngine.gameObjects.components.gui.guiComponents.GUIText;
import mEngine.util.TimeHelper;

public class FPSTextComponent extends GUIText {

    public FPSTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = String.valueOf("mEngine Test Run @ " + TimeHelper.FPS + " FPS");

    }

}
