package mEngine.util.debug.graphs;

import mEngine.gameObjects.modules.gui.modules.GUIGraph;
import mEngine.util.debug.Profiler;

public class RAMGraphModule extends GUIGraph {


    public RAMGraphModule(double[] values, String textureName) {

        super(values, textureName);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getMemoryGraph((int) parent.getSize().x);
        super.onUpdate();

    }
}
