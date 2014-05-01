package mEngine.util.debug.texts.position;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.gui.guiComponents.GUIText;

public class PositionYTextComponent extends GUIText {

    public PositionYTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = "y: " + parent.parent.position.y;

    }

}
