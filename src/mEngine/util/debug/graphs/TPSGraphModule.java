package mEngine.util.debug.graphs;

import mEngine.gameObjects.modules.gui.modules.GUIGraph;
import mEngine.util.debug.Profiler;

public class TPSGraphModule extends GUIGraph {


    public TPSGraphModule(double[] values, String textureName) {

        super(values, textureName);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getTPSGraph((int) parent.getSize().x);
        super.onUpdate();

    }

}