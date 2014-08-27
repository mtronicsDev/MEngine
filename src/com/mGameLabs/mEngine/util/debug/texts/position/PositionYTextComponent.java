package com.mGameLabs.mEngine.util.debug.texts.position;

import com.mGameLabs.mEngine.core.ObjectController;
import com.mGameLabs.mEngine.gameObjects.GameObject;
import com.mGameLabs.mEngine.gameObjects.components.gui.guiComponents.GUIText;

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
