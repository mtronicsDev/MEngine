package mEngine.util.debug.texts.position;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.gui.guiComponents.GUIText;

public class PositionXTextComponent extends GUIText {

    public PositionXTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        GameObject obj = ObjectController.getGameObject(0);
        text = "x: " + obj.position.x;

    }

}