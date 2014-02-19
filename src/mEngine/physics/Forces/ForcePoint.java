package mEngine.physics.forces;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ForcePoint {

    public Vector3f position;
    public List<Force> forces = new ArrayList<Force>();

    public ForcePoint(Vector3f pos) {

        position = pos;

    }

}
