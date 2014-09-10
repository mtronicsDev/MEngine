package mEngine.gameObjects.modules.renderable;

import mEngine.graphics.renderable.materials.Material3D;

public abstract class ModuleRenderable3D extends ModuleRenderable {

    public Material3D material;

    public abstract void render();

    public abstract void addToRenderQueue();

}
