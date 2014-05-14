package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.materials.Material3D;
import mEngine.graphics.renderable.models.Model;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

public class RenderComponent extends ComponentRenderable3D {

    public Model model;
    String modelFileName;
    Vector3f offset;

    public RenderComponent(String modelFileName) {

        this(modelFileName, true);

    }

    public RenderComponent(String modelFileName, boolean hasTexture) {

        this(modelFileName, new Vector3f(), modelFileName);
        if (!hasTexture) material.setTextureName(null);

    }

    public RenderComponent(String modelFileName, Material3D material) {

        this(modelFileName, new Vector3f(), material);

    }

    public RenderComponent(String modelFileName, Vector3f offset) {

        this(modelFileName, offset, true);

    }

    public RenderComponent(String modelFileName, Vector3f offset, boolean hasTexture) {

        this(modelFileName, offset, modelFileName);
        if (!hasTexture) material.setTextureName(null);

    }

    public RenderComponent(String modelFileName, Vector3f offset, Material3D material) {

        this.modelFileName = modelFileName;
        this.offset = offset;
        this.material = material;

    }

    public RenderComponent(String modelFileName, Vector3f offset, String textureName) {

        this.modelFileName = modelFileName;
        this.offset = offset;
        this.material = new Material3D();

        material.setTextureName(textureName);

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);

        model = new Model(modelFileName, this, true);

    }

    public void onUpdate() {

        model.update(VectorHelper.sumVectors(new Vector3f[]{parent.position, offset}), parent.rotation);

    }

    @Override
    public void onSave() {

        super.onSave();
        model = null; //Delete unserializable model

    }

    @Override
    public void onLoad() {

        super.onLoad();

        model = new Model(modelFileName, this, true); //Create model again

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(model);

    }
}
