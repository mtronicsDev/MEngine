package com.mGameLabs.mEngine.util.debug.texts.position;

import com.mGameLabs.mEngine.core.ObjectController;
import com.mGameLabs.mEngine.gameObjects.GameObject;
import com.mGameLabs.mEngine.gameObjects.components.gui.guiComponents.GUIText;

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
