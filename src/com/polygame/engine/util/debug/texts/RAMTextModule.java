/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.util.debug.texts;

import com.polygame.engine.gameObjects.modules.gui.modules.GUIText;
import com.polygame.engine.util.math.NumberFormatHelper;

import static com.polygame.engine.util.debug.RuntimeHelper.*;

public class RAMTextModule extends GUIText {

    public RAMTextModule(int fontSize) {
        super("RAM", fontSize);
    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        text = String.valueOf("Memory usage: "
          + NumberFormatHelper.cutDecimals((float) getMemoryStats(USED_MEMORY) / getMemoryStats(TOTAL_MEMORY) * 100, 1)
          + "% [" + getMemoryStats(TOTAL_MEMORY) + " MB]");

    }

}
