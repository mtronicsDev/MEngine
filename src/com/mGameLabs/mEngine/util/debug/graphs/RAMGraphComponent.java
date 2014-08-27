package com.mGameLabs.mEngine.util.debug.graphs;

import com.mGameLabs.mEngine.gameObjects.components.gui.guiComponents.GUIGraph;
import com.mGameLabs.mEngine.util.debug.Profiler;

public class RAMGraphComponent extends GUIGraph {


    public RAMGraphComponent(double[] values, String textureName) {

        super(values, textureName);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getMemoryGraph((int) parent.getSize().x);
        super.onUpdate();

    }
}
