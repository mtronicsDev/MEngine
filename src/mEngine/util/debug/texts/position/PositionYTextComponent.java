package mEngine.util.debug.texts.position;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.gui.modules.GUIText;

public class PositionYTextComponent extends GUIText {

    public PositionYTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        GameObject obj = ObjectController.getGameObject(0);
        text = "y: " + obj.position.y;

    }

}
