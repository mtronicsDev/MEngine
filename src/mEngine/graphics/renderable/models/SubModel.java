/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.graphics.renderable.models;

import mEngine.graphics.renderable.materials.Material3D;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

public class SubModel {

    public Material3D material;
    public ArrayList<Vector3f> vertices;
    public ArrayList<Vector3f> normals;
    public ArrayList<Vector2f> uvs;
    public ArrayList<Face> faces;

    public SubModel() {
        vertices = new ArrayList<Vector3f>();
        normals = new ArrayList<Vector3f>();
        uvs = new ArrayList<Vector2f>();
        faces = new ArrayList<Face>();
    }

    public SubModel(Material3D material) {
        this.material = material;

        vertices = new ArrayList<Vector3f>();
        normals = new ArrayList<Vector3f>();
        uvs = new ArrayList<Vector2f>();
        faces = new ArrayList<Face>();
    }

}
