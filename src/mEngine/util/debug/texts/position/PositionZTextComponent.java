package mEngine.util.debug.texts.position;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.gui.guiComponents.GUIText;

public class PositionZTextComponent extends GUIText {

    public PositionZTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = "z: " + parent.parent.position.z;

    }

}
