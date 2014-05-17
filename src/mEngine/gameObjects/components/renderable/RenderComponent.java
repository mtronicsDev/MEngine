package mEngine.gameObjects.components.renderable;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.models.Face;
import mEngine.graphics.renderable.models.Model;
import mEngine.graphics.renderable.models.SubModel;
import mEngine.util.rendering.ModelHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class RenderComponent extends ComponentRenderable3D {

    public Model model;
    String modelFileName;
    Vector3f offset;
    boolean[] displayListFactors = new boolean[]{false, false};
    int displayListIndex;

    public RenderComponent(String modelFileName) {

        this(modelFileName, true);

    }

    public RenderComponent(String modelFileName, boolean isStatic) {

        this(modelFileName, isStatic, new Vector3f());

    }

    public RenderComponent(String modelFileName, boolean isStatic, Vector3f offset) {

        this.modelFileName = modelFileName;
        displayListFactors[1] = isStatic;
        this.offset = offset;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);

        model = ModelHelper.getModel(modelFileName);

    }

    public void onUpdate() {

        //model.update(VectorHelper.sumVectors(new Vector3f[]{parent.position, offset}), parent.rotation);

    }

    public void render() {

        for (SubModel subModel : model.subModels) {

            if (subModel.material.hasTexture() && subModel.material.getTexture() == null)
                subModel.material.setTextureFromName();

        }

        if (displayListFactors[0] && displayListFactors[1]) {

            List<Vector3f> renderVertices = new ArrayList<Vector3f>();
            List<Vector3f> renderNormals = new ArrayList<Vector3f>();
            List<Vector2f> renderUVs = new ArrayList<Vector2f>();

            for (SubModel subModel : model.subModels) {
                for (Face face : subModel.faces) {

                    Vector3f v1 = subModel.vertices.get((int) face.vertexIndices.x);
                    renderVertices.add(v1);

                    Vector3f v2 = subModel.vertices.get((int) face.vertexIndices.y);
                    renderVertices.add(v2);

                    Vector3f v3 = subModel.vertices.get((int) face.vertexIndices.z);
                    renderVertices.add(v3);

                    Vector2f uv1 = subModel.uvs.get((int) face.uvIndices.x);
                    renderUVs.add(new Vector2f(uv1.x, 1 - uv1.y));

                    Vector2f uv2 = subModel.uvs.get((int) face.uvIndices.y);
                    renderUVs.add(new Vector2f(uv2.x, 1 - uv2.y));

                    Vector2f uv3 = subModel.uvs.get((int) face.uvIndices.z);
                    renderUVs.add(new Vector2f(uv3.x, 1 - uv3.y));

                    Vector3f n1 = subModel.normals.get((int) face.normalIndices.x);
                    renderNormals.add(n1);

                    Vector3f n2 = subModel.normals.get((int) face.normalIndices.y);
                    renderNormals.add(n2);

                    Vector3f n3 = subModel.normals.get((int) face.normalIndices.z);
                    renderNormals.add(n3);

                }

                displayListIndex = Renderer.displayListCounter;
                displayListFactors[1] = true;
                Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, subModel.material, Renderer.RENDER_TRIANGLES);

            }

        }
        if (displayListFactors[0] && displayListFactors[1]) {

            for (int i = 0; i < model.subModels.size(); i++) {

                Renderer.renderObject3D(displayListIndex + i, parent.position, parent.rotation, model.subModels.get(i).material, 0);

            }

        } else {

            List<Vector3f> renderVertices = new ArrayList<Vector3f>();
            List<Vector3f> renderNormals = new ArrayList<Vector3f>();
            List<Vector2f> renderUVs = new ArrayList<Vector2f>();

            for (SubModel subModel : model.subModels) {
                for (Face face : subModel.faces) {

                    Vector3f v1 = subModel.vertices.get((int) face.vertexIndices.x);
                    renderVertices.add(v1);

                    Vector3f v2 = subModel.vertices.get((int) face.vertexIndices.y);
                    renderVertices.add(v2);

                    Vector3f v3 = subModel.vertices.get((int) face.vertexIndices.z);
                    renderVertices.add(v3);

                    Vector2f uv1 = subModel.uvs.get((int) face.uvIndices.x);
                    renderUVs.add(new Vector2f(uv1.x, 1 - uv1.y));

                    Vector2f uv2 = subModel.uvs.get((int) face.uvIndices.y);
                    renderUVs.add(new Vector2f(uv2.x, 1 - uv2.y));

                    Vector2f uv3 = subModel.uvs.get((int) face.uvIndices.z);
                    renderUVs.add(new Vector2f(uv3.x, 1 - uv3.y));

                    Vector3f n1 = subModel.normals.get((int) face.normalIndices.x);
                    renderNormals.add(n1);

                    Vector3f n2 = subModel.normals.get((int) face.normalIndices.y);
                    renderNormals.add(n2);

                    Vector3f n3 = subModel.normals.get((int) face.normalIndices.z);
                    renderNormals.add(n3);

                }

                Renderer.renderObject3D(renderVertices, renderNormals, renderUVs, subModel.material, Renderer.RENDER_TRIANGLES, 0);

            }

        }

    }

    @Override
    public void onSave() {

        super.onSave();
        model = null; //Delete unserializable model

    }

    @Override
    public void onLoad() {

        super.onLoad();
        model = ModelHelper.getModel(modelFileName); //Create model again

    }

    @Override
    public void addToRenderQueue() {

        Renderer.currentRenderQueue.addModel(this);

    }

}
