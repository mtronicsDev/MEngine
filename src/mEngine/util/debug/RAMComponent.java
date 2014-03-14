package mEngine.util.debug;

import mEngine.gameObjects.components.gui.guiComponents.GUIGraph;
import mEngine.util.debug.profiler.Profiler;
import org.lwjgl.util.vector.Vector2f;

public class RAMComponent extends GUIGraph {


    public RAMComponent(Vector2f size, double[] values) {

        super(size, values);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getMemoryGraph((int) size.x);
        super.onUpdate();

    }
}
