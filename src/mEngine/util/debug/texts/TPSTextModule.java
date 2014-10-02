/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.debug.texts;

import mEngine.gameObjects.modules.gui.modules.GUIText;
import mEngine.util.time.TimeHelper;

public class TPSTextModule extends GUIText {

    public TPSTextModule(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = String.valueOf("Ticks per second: " + TimeHelper.TPS + " TPS");

    }

}
