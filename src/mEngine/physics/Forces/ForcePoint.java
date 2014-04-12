package mEngine.physics.forces;

import org.lwjgl.util.vector.Vector3f;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ForcePoint implements Serializable {

    public Vector3f position;
    public Map<String, Force> forces = new HashMap<String, Force>();
    public int forceCount = 0;

    public ForcePoint(Vector3f pos) {

        position = pos;

    }

}
