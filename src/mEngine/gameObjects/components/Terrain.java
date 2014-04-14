package mEngine.gameObjects.components;

import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.Model;

public class Terrain extends ComponentRenderable {

    protected Model model;

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(model);

    }

}
