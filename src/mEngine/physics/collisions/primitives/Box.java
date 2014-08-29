package mEngine.physics.collisions.primitives;

import org.lwjgl.util.vector.Vector3f;

public class Box {

    public Vector3f position;
    public Vector3f size;

    public Box(Vector3f pos, Vector3f size) {

        position = pos;
        this.size = size;

    }

}
