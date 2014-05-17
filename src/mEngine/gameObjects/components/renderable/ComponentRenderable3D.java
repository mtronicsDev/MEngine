package mEngine.gameObjects.components.renderable;

import mEngine.graphics.renderable.materials.Material3D;

public abstract class ComponentRenderable3D extends ComponentRenderable {

    public Material3D material;

    public abstract void render();

    public abstract void addToRenderQueue();

}
