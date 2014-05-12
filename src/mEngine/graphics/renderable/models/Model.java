package mEngine.graphics.renderable.models;

import mEngine.gameObjects.components.renderable.ComponentRenderable3D;
import mEngine.graphics.Renderer;
import mEngine.graphics.renderable.materials.Material3D;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.rendering.ModelHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Model implements Serializable {

    public List<Vector3f> vertices = new ArrayList<Vector3f>();
    public List<Vector3f> normals = new ArrayList<Vector3f>();
    public List<Vector2f> uvs = new ArrayList<Vector2f>();
    public List<Face> faces = new ArrayList<Face>();

    public float mass;
    public int renderMode = Renderer.RENDER_TRIANGLES;
    public boolean[] displayListFactors = new boolean[]{false, false};
    public int displayListIndex;

    public ComponentRenderable3D parent;
    public Vector3f position;
    public Vector3f rotation;

    public Model(String fileName, ComponentRenderable3D parent, boolean isStatic) {

        Model model = ModelHelper.loadModelSafely(fileName, isStatic);

        this.vertices = model.vertices;
        this.normals = model.normals;
        this.uvs = model.uvs;
        this.faces = model.faces;
        this.mass = model.getMass();
        this.displayListFactors[0] = isStatic;
        this.parent = parent;

        Vector3f middle = getMiddle();

        for (int count = 0; count < this.vertices.size(); count++)
            this.vertices.set(count, VectorHelper.subtractVectors(this.vertices.get(count), middle));

        position = VectorHelper.sumVectors(new Vector3f[]{parent.parent.position, middle});

        if (!displayListFactors[0]) displayListIndex = -1;

    }

    public Model(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, List<Face> faces, ComponentRenderable3D parent, boolean isStatic) {

        this.displayListFactors[0] = isStatic;
        this.vertices = vertices;
        this.normals = normals;
        this.uvs = uvs;
        this.faces = faces;
        this.mass = getMass();
        this.parent = parent;

        Vector3f middle = getMiddle();

        for (int count = 0; count < this.vertices.size(); count++)
            this.vertices.set(count, VectorHelper.subtractVectors(this.vertices.get(count), middle));

        if (parent != null)
            position = VectorHelper.sumVectors(new Vector3f[]{parent.parent.position, middle});

        if (!displayListFactors[0]) displayListIndex = -1;

    }

    public void render() {

        if (parent.material.hasTexture() && parent.material.getTexture() == null) {
            parent.material.setTextureFromName();
        }

        if (displayListFactors[0] && !displayListFactors[1]) {

            List<Vector3f> renderVertices = new ArrayList<Vector3f>();
            List<Vector3f> renderNormals = new ArrayList<Vector3f>();
            List<Vector2f> renderUVs = new ArrayList<Vector2f>();

            for (Face face : faces) {


                Vector3f v1 = vertices.get((int) face.vertexIndices.x);
                renderVertices.add(v1);

                Vector3f v2 = vertices.get((int) face.vertexIndices.y);
                renderVertices.add(v2);

                Vector3f v3 = vertices.get((int) face.vertexIndices.z);
                renderVertices.add(v3);

                if (uvs.size() != 0) {
                    Vector2f uv1 = uvs.get((int) face.uvIndices.x);
                    renderUVs.add(new Vector2f(uv1.x, 1 - uv1.y));

                    Vector2f uv2 = uvs.get((int) face.uvIndices.y);
                    renderUVs.add(new Vector2f(uv2.x, 1 - uv2.y));

                    Vector2f uv3 = uvs.get((int) face.uvIndices.z);
                    renderUVs.add(new Vector2f(uv3.x, 1 - uv3.y));
                } else {

                    for (int i = 0; i < 3; i++) renderUVs.add(new Vector2f(0, 0));

                }


                Vector3f n1 = normals.get((int) face.normalIndices.x);
                renderNormals.add(n1);

                Vector3f n2 = normals.get((int) face.normalIndices.y);
                renderNormals.add(n2);

                Vector3f n3 = normals.get((int) face.normalIndices.z);
                renderNormals.add(n3);

            }

            displayListIndex = Renderer.displayListCounter;
            Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, parent.material, renderMode);

            displayListFactors[1] = true;

        }

        if (displayListFactors[0] && displayListFactors[1]) {

            Renderer.renderObject3D(displayListIndex, vertices.size(), position, parent.parent.rotation, parent.material, 0);

        } else {

            List<Vector3f> renderVertices = new ArrayList<Vector3f>();
            List<Vector3f> renderNormals = new ArrayList<Vector3f>();
            List<Vector2f> renderUVs = new ArrayList<Vector2f>();

            for (Face face : faces) {

                Vector2f uv1 = uvs.get((int) face.uvIndices.x);
                renderUVs.add(new Vector2f(uv1.x, 1 - uv1.y));

                Vector3f v1 = VectorHelper.sumVectors(new Vector3f[]{vertices.get((int) face.vertexIndices.x), position});
                renderVertices.add(v1);

                Vector3f n1 = normals.get((int) face.normalIndices.x);
                renderNormals.add(n1);


                Vector2f uv2 = uvs.get((int) face.uvIndices.y);
                renderUVs.add(new Vector2f(uv2.x, 1 - uv2.y));

                Vector3f v2 = VectorHelper.sumVectors(new Vector3f[]{vertices.get((int) face.vertexIndices.y), position});
                renderVertices.add(v2);

                Vector3f n2 = normals.get((int) face.normalIndices.y);
                renderNormals.add(n2);


                Vector2f uv3 = uvs.get((int) face.uvIndices.z);
                renderUVs.add(new Vector2f(uv3.x, 1 - uv3.y));

                Vector3f v3 = VectorHelper.sumVectors(new Vector3f[]{vertices.get((int) face.vertexIndices.z), position});
                renderVertices.add(v3);

                Vector3f n3 = normals.get((int) face.normalIndices.z);
                renderNormals.add(n3);

            }

            Renderer.renderObject3D(renderVertices, renderNormals, renderUVs, parent.material, renderMode, 0);

        }

    }

    public void update(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

    }

    public Vector3f getSize() {

        Vector3f size = new Vector3f();
        Vector3f maxVertexPos = new Vector3f();
        Vector3f minVertexPos = new Vector3f();

        for (Vector3f vertex : vertices) {

            if (vertex.x > maxVertexPos.x) maxVertexPos.x = vertex.x;
            else if (vertex.x < minVertexPos.x) minVertexPos.x = vertex.x;

            if (vertex.y > maxVertexPos.y) maxVertexPos.y = vertex.y;
            else if (vertex.y < minVertexPos.y) minVertexPos.y = vertex.y;

            if (vertex.z > maxVertexPos.z) maxVertexPos.z = vertex.z;
            else if (vertex.z < minVertexPos.z) minVertexPos.z = vertex.z;

        }

        size.x = maxVertexPos.x - minVertexPos.x;
        size.y = maxVertexPos.y - minVertexPos.y;
        size.z = maxVertexPos.z - minVertexPos.z;

        return size;

    }

    public Vector3f[] getExtremeVertexPositions() {

        Vector3f maxVertexPos = new Vector3f();
        Vector3f minVertexPos = new Vector3f();

        for (Vector3f vertex : vertices) {

            if (vertex.x > maxVertexPos.x) maxVertexPos.x = vertex.x;
            else if (vertex.x < minVertexPos.x) minVertexPos.x = vertex.x;

            if (vertex.y > maxVertexPos.y) maxVertexPos.y = vertex.y;
            else if (vertex.y < minVertexPos.y) minVertexPos.y = vertex.y;

            if (vertex.z > maxVertexPos.z) maxVertexPos.z = vertex.z;
            else if (vertex.z < minVertexPos.z) minVertexPos.z = vertex.z;

        }

        return new Vector3f[]{minVertexPos, maxVertexPos};

    }

    public Vector3f getMiddle() {

        Vector3f minVertexPos = getExtremeVertexPositions()[0];

        for (int count = 0; count < vertices.size(); count++)
            vertices.set(count, VectorHelper.subtractVectors(vertices.get(count), minVertexPos));

        return VectorHelper.divideVectors(getExtremeVertexPositions()[1], new Vector3f(2, 2, 2));

    }


    private float getMass() {

        return 60;

    }

}

