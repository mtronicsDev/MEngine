package mEngine.util.debug.graphs;

import mEngine.gameObjects.components.gui.guiComponents.GUIGraph;
import mEngine.util.debug.profiler.Profiler;
import org.lwjgl.util.vector.Vector2f;

public class TPSGraphComponent extends GUIGraph {


    public TPSGraphComponent(Vector2f size, double[] values) {

        super(size, values);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getTPSGraph((int) size.x);
        super.onUpdate();

    }

}
