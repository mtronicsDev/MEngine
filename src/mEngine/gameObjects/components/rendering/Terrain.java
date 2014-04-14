package mEngine.gameObjects.components.rendering;

import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.Model;

public class Terrain extends ComponentRenderable {

    protected Model model;

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(model);

    }

}
