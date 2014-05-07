package mEngine.graphics.renderable;

import mEngine.graphics.GraphicsController;
import mEngine.graphics.Renderer;
import mEngine.util.math.vectors.Matrix3d;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.rendering.ModelHelper;
import mEngine.util.rendering.TextureHelper;
import mEngine.util.resources.ResourceHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model implements Serializable {

    public List<Vector3f> vertices = new ArrayList<Vector3f>();
    public List<Vector3f> normals = new ArrayList<Vector3f>();
    public List<Vector2f> uvs = new ArrayList<Vector2f>();
    public List<Face> faces = new ArrayList<Face>();

    public float mass;
    public Vector3f position = new Vector3f();
    public Vector3f rotation = new Vector3f();
    public int renderMode = Renderer.RENDER_TRIANGLES;
    public boolean[] displayListFactors = new boolean[]{false, false};
    public int displayListIndex;
    String textureName;
    Texture texture;
    boolean isTextureThere = true;

    public Model(String fileName, Vector3f pos, Vector3f rot, boolean isStatic) {

        Model model = ModelHelper.loadModelSafely(fileName, isStatic);
        textureName = fileName;

        this.vertices = model.vertices;
        this.normals = model.normals;
        this.uvs = model.uvs;
        this.faces = model.faces;
        this.texture = model.texture;
        this.mass = model.getMass();
        this.displayListFactors[0] = true;

        Vector3f middle = getMiddle();

        for (int count = 0; count < this.vertices.size(); count++)
            this.vertices.set(count, VectorHelper.subtractVectors(this.vertices.get(count), middle));

        position = pos;
        rotation = rot;

        position = VectorHelper.sumVectors(new Vector3f[]{position, middle});

        if (!displayListFactors[0]) displayListIndex = -1;

    }

    public Model(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, List<Face> faces, Texture texture, boolean isStatic) {

        this.displayListFactors[0] = isStatic;
        this.vertices = vertices;
        this.normals = normals;
        this.uvs = uvs;
        this.faces = faces;
        this.texture = texture;
        this.mass = getMass();

        Vector3f middle = getMiddle();

        for (int count = 0; count < this.vertices.size(); count++)
            this.vertices.set(count, VectorHelper.subtractVectors(this.vertices.get(count), middle));

        position = middle;

        if (!displayListFactors[0]) displayListIndex = -1;

    }

    public Model(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, List<Face> faces, Texture texture, Vector3f pos, boolean isStatic) {

        this.displayListFactors[0] = isStatic;
        this.vertices = vertices;
        this.normals = normals;
        this.uvs = uvs;
        this.faces = faces;
        this.texture = texture;
        this.mass = getMass();

        Vector3f middle = getMiddle();

        for (int count = 0; count < this.vertices.size(); count++)
            this.vertices.set(count, VectorHelper.subtractVectors(this.vertices.get(count), middle));

        position = pos;

        position = VectorHelper.sumVectors(new Vector3f[]{position, middle});

        if (!displayListFactors[0]) displayListIndex = -1;

    }

    public Model(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, List<Face> faces, String textureName, boolean isStatic) {

        this.displayListFactors[0] = isStatic;
        this.vertices = vertices;
        this.normals = normals;
        this.uvs = uvs;
        this.faces = faces;
        this.textureName = textureName;
        this.mass = getMass();

        Vector3f middle = getMiddle();

        for (int count = 0; count < this.vertices.size(); count++)
            this.vertices.set(count, VectorHelper.subtractVectors(this.vertices.get(count), middle));

        position = middle;

        if (!displayListFactors[0]) displayListIndex = -1;

    }

    public Model(List<Vector3f> vertices, List<Vector3f> normals, List<Vector2f> uvs, List<Face> faces, String textureName, Vector3f pos, boolean isStatic) {

        this.displayListFactors[0] = isStatic;
        this.vertices = vertices;
        this.normals = normals;
        this.uvs = uvs;
        this.faces = faces;
        this.textureName = textureName;
        this.mass = getMass();

        Vector3f middle = getMiddle();

        for (int count = 0; count < this.vertices.size(); count++)
            this.vertices.set(count, VectorHelper.subtractVectors(this.vertices.get(count), middle));

        position = pos;

        position = VectorHelper.sumVectors(new Vector3f[]{position, middle});

        if (!displayListFactors[0]) displayListIndex = -1;

    }

    public void render() {

        if (texture == null && isTextureThere) {

            File textureFile = ResourceHelper.getResource(textureName, ResourceHelper.RES_TEXTURE);

            if (!textureFile.exists()) isTextureThere = false;

            else texture = TextureHelper.getTexture(textureName);

        }

        if (displayListFactors[0] && !displayListFactors[1]) {

            List<Vector3f> renderVertices = new ArrayList<Vector3f>();
            List<Vector3f> renderNormals = new ArrayList<Vector3f>();

            if (isTextureThere) {

                List<Vector2f> renderUVs = new ArrayList<Vector2f>();

                for (Face face : faces) {

                    Vector2f uv1 = uvs.get((int) face.uvIndices.x);
                    renderUVs.add(new Vector2f(uv1.x, 1 - uv1.y));

                    Vector3f v1 = vertices.get((int) face.vertexIndices.x);
                    renderVertices.add(v1);

                    Vector3f n1 = normals.get((int) face.normalIndices.x);
                    renderNormals.add(n1);


                    Vector2f uv2 = uvs.get((int) face.uvIndices.y);
                    renderUVs.add(new Vector2f(uv2.x, 1 - uv2.y));

                    Vector3f v2 = vertices.get((int) face.vertexIndices.y);
                    renderVertices.add(v2);

                    Vector3f n2 = normals.get((int) face.normalIndices.y);
                    renderNormals.add(n2);


                    Vector2f uv3 = uvs.get((int) face.uvIndices.z);
                    renderUVs.add(new Vector2f(uv3.x, 1 - uv3.y));

                    Vector3f v3 = vertices.get((int) face.vertexIndices.z);
                    renderVertices.add(v3);

                    Vector3f n3 = normals.get((int) face.normalIndices.z);
                    renderNormals.add(n3);

                }

                displayListIndex = Renderer.displayListCounter;
                Renderer.addDisplayList(renderVertices, renderNormals, renderUVs, texture, renderMode);

            } else {

                for (Face face : faces) {

                    Vector3f v1 = vertices.get((int) face.vertexIndices.x);
                    renderVertices.add(v1);

                    Vector3f n1 = normals.get((int) face.normalIndices.x);
                    renderNormals.add(n1);


                    Vector3f v2 = vertices.get((int) face.vertexIndices.y);
                    renderVertices.add(v2);

                    Vector3f n2 = normals.get((int) face.normalIndices.y);
                    renderNormals.add(n2);


                    Vector3f v3 = vertices.get((int) face.vertexIndices.z);
                    renderVertices.add(v3);

                    Vector3f n3 = normals.get((int) face.normalIndices.z);
                    renderNormals.add(n3);

                }

                displayListIndex = Renderer.displayListCounter;
                Renderer.addDisplayList(renderVertices, renderNormals, renderMode);

            }

            displayListFactors[1] = true;

        }


        if (displayListFactors[0] && displayListFactors[1]) {

            Renderer.renderObject3D(displayListIndex, position, rotation, isTextureThere, 0);

        } else {

            List<Vector3f> renderVertices = new ArrayList<Vector3f>();
            List<Vector3f> renderNormals = new ArrayList<Vector3f>();

            if (!isTextureThere) {

                for (Face face : faces) {

                    Vector3f v1 = VectorHelper.sumVectors(new Vector3f[]{vertices.get((int) face.vertexIndices.x), position});
                    renderVertices.add(v1);

                    Vector3f n1 = normals.get((int) face.normalIndices.x);
                    renderNormals.add(n1);


                    Vector3f v2 = VectorHelper.sumVectors(new Vector3f[]{vertices.get((int) face.vertexIndices.y), position});
                    renderVertices.add(v2);

                    Vector3f n2 = normals.get((int) face.normalIndices.y);
                    renderNormals.add(n2);


                    Vector3f v3 = VectorHelper.sumVectors(new Vector3f[]{vertices.get((int) face.vertexIndices.z), position});
                    renderVertices.add(v3);

                    Vector3f n3 = normals.get((int) face.normalIndices.z);
                    renderNormals.add(n3);

                }

                if (GraphicsController.isWireFrameMode)
                    Renderer.renderObject3D(renderVertices, renderNormals, Renderer.RENDER_LINE_STRIP, 0);

                else Renderer.renderObject3D(renderVertices, renderNormals, renderMode, 0);

            } else {

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

                if (GraphicsController.isWireFrameMode)
                    Renderer.renderObject3D(renderVertices, renderNormals, renderUVs, texture, Renderer.RENDER_LINE_STRIP, 0);

                else Renderer.renderObject3D(renderVertices, renderNormals, renderUVs, texture, renderMode, 0);

            }

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

