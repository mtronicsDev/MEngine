/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.debug.graphs;

import mEngine.gameObjects.modules.gui.modules.GUIGraph;
import mEngine.util.debug.Profiler;

public class FPSGraphModule extends GUIGraph {


    public FPSGraphModule(double[] values, String textureName) {

        super(values, textureName);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getFPSGraph((int) parent.getSize().x);
        super.onUpdate();

    }

}
