package com.mGameLabs.mEngine.util.debug.graphs;

import com.mGameLabs.mEngine.gameObjects.components.gui.guiComponents.GUIGraph;
import com.mGameLabs.mEngine.util.debug.Profiler;

public class FPSGraphComponent extends GUIGraph {


    public FPSGraphComponent(double[] values, String textureName) {

        super(values, textureName);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getFPSGraph((int) parent.getSize().x);
        super.onUpdate();

    }

}
