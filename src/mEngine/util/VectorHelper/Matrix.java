package mEngine.util.vectorHelper;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Matrix {

    public Vector3f firstLine3d, secondLine3d, thirdLine3d;

    public Vector2f firstLine2d, secondLine2d;

    public Matrix(Vector3f firstLine3d, Vector3f secondLine3d, Vector3f thirdLine3d) {

        this.firstLine3d = firstLine3d;
        this.secondLine3d = secondLine3d;
        this.thirdLine3d = thirdLine3d;

    }

    public Matrix(Vector2f firstLine2d, Vector2f secondLine2d) {

        this.firstLine2d = firstLine2d;
        this.secondLine2d = secondLine2d;

    }

}
