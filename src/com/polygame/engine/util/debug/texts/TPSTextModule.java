/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.util.debug.texts;

import com.polygame.engine.gameObjects.modules.gui.modules.GUIText;
import com.polygame.engine.util.time.TimeHelper;

public class TPSTextModule extends GUIText {

    public TPSTextModule(int fontSize) {
        super("TPS", fontSize);
    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = String.valueOf("Ticks per second: " + TimeHelper.TPS + " TPS");

    }

}
