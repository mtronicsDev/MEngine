package mEngine.util.debug.graphs;

import mEngine.gameObjects.components.gui.guiComponents.GUIGraph;
import mEngine.util.debug.Profiler;

public class TPSGraphComponent extends GUIGraph {


    public TPSGraphComponent(double[] values, String textureName) {

        super(values, textureName);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getTPSGraph((int) parent.getSize().x);
        super.onUpdate();

    }

}
