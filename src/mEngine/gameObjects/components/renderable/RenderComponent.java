package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.physics.MovementComponent;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.Model;

public class RenderComponent extends ComponentRenderable {

    public Model model;
    String modelFileName;

    public RenderComponent(String modelFileName) {

        this.modelFileName = modelFileName;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        
        model = new Model(modelFileName, parent.position, parent.rotation, true);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        model.update(parent.position, parent.rotation);

    }

    @Override
    public void onSave() {

        super.onSave();
        model = null;

    }

    @Override
    public void onLoad() {

        super.onLoad();
        
        model = new Model(modelFileName, parent.position, parent.rotation, true);

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(model);

    }
}
