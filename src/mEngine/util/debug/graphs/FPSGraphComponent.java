package mEngine.util.debug.graphs;

import mEngine.gameObjects.modules.gui.modules.GUIGraph;
import mEngine.util.debug.Profiler;

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
