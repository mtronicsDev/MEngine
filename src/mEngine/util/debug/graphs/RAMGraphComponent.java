package mEngine.util.debug.graphs;

import mEngine.gameObjects.components.gui.guiComponents.GUIGraph;
import mEngine.util.debug.Profiler;
import org.lwjgl.util.vector.Vector2f;

public class RAMGraphComponent extends GUIGraph {


    public RAMGraphComponent(Vector2f size, double[] values, String textureName) {

        super(size, values, textureName);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getMemoryGraph((int) size.x);
        super.onUpdate();

    }
}
