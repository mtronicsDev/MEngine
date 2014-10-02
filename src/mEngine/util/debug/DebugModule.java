/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.debug;

import mEngine.gameObjects.modules.Module;

/**
 * Created by Maxi on 02.10.2014.
 */
public class DebugModule extends Module {

    @Override
    public void onUpdate() {
        super.onUpdate();
        System.out.println(parent.position.toString());
    }
}
