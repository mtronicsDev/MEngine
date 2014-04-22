package mEngine.util.debug.graphs;

import mEngine.gameObjects.components.gui.guiComponents.GUIGraph;
import mEngine.util.debug.profiler.Profiler;
import org.lwjgl.util.vector.Vector2f;

public class FPSGraphComponent extends GUIGraph {


    public FPSGraphComponent(Vector2f size, double[] values, String textureName) {

        super(size, values, textureName);

    }

    @Override
    public void onUpdate() {

        graph = Profiler.getFPSGraph((int) size.x);
        super.onUpdate();

    }

}
