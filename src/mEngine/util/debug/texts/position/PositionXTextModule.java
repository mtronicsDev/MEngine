/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.debug.texts.position;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.gui.modules.GUIText;

public class PositionXTextModule extends GUIText {

    public PositionXTextModule(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        GameObject obj = ObjectController.getGameObject(0);
        text = "x: " + obj.position.x;

    }

}
