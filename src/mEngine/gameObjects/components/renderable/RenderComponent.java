package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.MovementComponent;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.Model;

public class RenderComponent extends ComponentRenderable {

    public Model model;
    String modelFileName;

    public RenderComponent(String modelFileName) {

        this(modelFileName, false);

    }

    public RenderComponent(String modelFileName, boolean addedAsLast) {

        super(addedAsLast);
        this.modelFileName = modelFileName;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);

        boolean isStatic = true;

        for (Component component : parent.components.values()) {

            if (component instanceof MovementComponent) isStatic = false;

        }
        
        model = new Model(modelFileName, parent.position, parent.rotation, isStatic);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        model.update(parent.position, parent.rotation);

    }

    @Override
    public void onSave() {

        super.onSave();
        model = null; //Delete unserializable model

    }

    @Override
    public void onLoad() {

        super.onLoad();
        
        boolean isStatic = true;
        
        for (Component component : parent.components.values()) {
            
            if (component instanceof MovementComponent) isStatic = false;
            
        }
        
        model = new Model(modelFileName, parent.position, parent.rotation, isStatic); //Create model again

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(model);

    }
}
