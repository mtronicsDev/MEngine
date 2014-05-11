package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.models.Model;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

public class RenderComponent extends ComponentRenderable {

    public Model model;
    String modelFileName;
    Vector3f positionModifier;

    public RenderComponent(String modelFileName) {

        this(modelFileName, new Vector3f());

    }

    public RenderComponent(String modelFileName, Vector3f positionModifier) {

        this.modelFileName = modelFileName;
        this.positionModifier = positionModifier;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);

        model = new Model(modelFileName, parent.position, parent.rotation, true);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();
        model.update(VectorHelper.sumVectors(new Vector3f[] {parent.position, positionModifier}), parent.rotation);

    }

    @Override
    public void onSave() {

        super.onSave();
        model = null; //Delete unserializable model

    }

    @Override
    public void onLoad() {

        super.onLoad();

        model = new Model(modelFileName, parent.position, parent.rotation, true); //Create model again

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(model);

    }
}
