package mEngine.util.math.vectors;

import org.lwjgl.util.vector.Vector2f;

public class Matrix2f extends Matrix {

    private Vector2f firstLine;
    private Vector2f secondLine;

    public Matrix2f(Vector2f firstLine, Vector2f secondLine) {

        this.firstLine = firstLine;
        this.secondLine = secondLine;

    }

    public Vector2f multiplyByVector(Vector2f vector) {

        Vector2f result = new Vector2f();

        result.x = vector.x * firstLine.x + vector.y * firstLine.y;
        result.y = vector.x * secondLine.x + vector.y * secondLine.y;

        return result;

    }

}
