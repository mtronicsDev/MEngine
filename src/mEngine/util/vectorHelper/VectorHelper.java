package mEngine.util.vectorHelper;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class VectorHelper {

    public static boolean areEqual(Vector3f vectorA, Vector3f vectorB) {

        if(vectorA == null) vectorA = new Vector3f();
        if(vectorB == null) vectorB = new Vector3f();

        return vectorA.x == vectorB.x
                && vectorA.y == vectorB.y
                && vectorA.z == vectorB.z;

    }

    public static Vector3f sumVectors(Vector3f[] vectors) {

        for(Vector3f vector : vectors) {

            if(vector == null) vector = new Vector3f();

        }

        Vector3f vectorSum = new Vector3f();

        for(Vector3f vector : vectors) {

            vectorSum.x += vector.x;
            vectorSum.y += vector.y;
            vectorSum.z += vector.z;

        }

        return vectorSum;

    }

    public static Vector3f subtractVectors(Vector3f vectorA, Vector3f vectorB) {

        if(vectorA == null) vectorA = new Vector3f();
        if(vectorB == null) vectorB = new Vector3f();

        Vector3f vectorDifference = new Vector3f();

        vectorDifference.x = vectorA.x - vectorB.x;
        vectorDifference.y = vectorA.y - vectorB.y;
        vectorDifference.z = vectorA.z - vectorB.z;

        return  vectorDifference;

    }

    public static Vector3f multiplyVectors(Vector3f[] vectors) {

        for(Vector3f vector : vectors) {

            if(vector == null) vector = new Vector3f();

        }

        Vector3f vectorProduct = new Vector3f(1, 1, 1);

        for(Vector3f vector : vectors) {

            vectorProduct.x *= vector.x;
            vectorProduct.y *= vector.y;
            vectorProduct.z *= vector.z;

        }

        return vectorProduct;

    }

    public static Vector3f divideVectors(Vector3f vectorA, Vector3f vectorB) {

        if(vectorA == null) vectorA = new Vector3f();
        if(vectorB == null) vectorB = new Vector3f();

        Vector3f vectorQuotient = new Vector3f();

        vectorQuotient.x = vectorA.x / vectorB.x;
        vectorQuotient.y = vectorA.y / vectorB.y;
        vectorQuotient.z = vectorA.z / vectorB.z;

        return  vectorQuotient;

    }

    public static float getAbs(Vector3f vector) {

        if(vector == null) vector = new Vector3f();

        float abs;

        abs = (float)Math.sqrt(getScalarProduct(vector, vector));

        return  abs;

    }

    public static float getScalarProduct(Vector3f vectorA, Vector3f vectorB) {

        if(vectorA == null) vectorA = new Vector3f();
        if(vectorB == null) vectorB = new Vector3f();

        float scalarProduct;

        scalarProduct = vectorA.x * vectorB.x + vectorA.y * vectorB.y + vectorA.z * vectorB.z;

        return scalarProduct;

    }

    public static boolean areEqual(Vector2f vectorA, Vector2f vectorB) {

        if(vectorA == null) vectorA = new Vector2f();
        if(vectorB == null) vectorB = new Vector2f();

        return vectorA.x == vectorB.x
                && vectorA.y == vectorB.y;

    }

    public static Vector2f sumVectors(Vector2f[] vectors) {

        for(Vector2f vector : vectors) {

            if(vector == null) vector = new Vector2f();

        }

        Vector2f vectorSum = new Vector2f();

        for(Vector2f vector : vectors) {

            vectorSum.x += vector.x;
            vectorSum.y += vector.y;

        }

        return vectorSum;

    }

    public static Vector2f subtractVectors(Vector2f vectorA, Vector2f vectorB) {

        if(vectorA == null) vectorA = new Vector2f();
        if(vectorB == null) vectorB = new Vector2f();

        Vector2f vectorDifference = new Vector2f();

        vectorDifference.x = vectorA.x - vectorB.x;
        vectorDifference.y = vectorA.y - vectorB.y;

        return  vectorDifference;

    }

    public static Vector2f multiplyVectors(Vector2f[] vectors) {

        for(Vector2f vector : vectors) {

            if(vector == null) vector = new Vector2f();

        }

        Vector2f vectorProduct = new Vector2f();

        for(Vector2f vector : vectors) {

            vectorProduct.x *= vector.x;
            vectorProduct.y *= vector.y;

        }

        return vectorProduct;

    }

    public static Vector2f divideVectors(Vector2f vectorA, Vector2f vectorB) {

        if(vectorA == null) vectorA = new Vector2f();
        if(vectorB == null) vectorB = new Vector2f();

        Vector2f vectorQuotient = new Vector2f();

        vectorQuotient.x = vectorA.x / vectorB.x;
        vectorQuotient.y = vectorA.y / vectorB.y;

        return  vectorQuotient;

    }

    public static float getAbs(Vector2f vector) {

        if(vector == null) vector = new Vector2f();

        float abs;

        abs = (float)Math.sqrt(getScalarProduct(vector, vector));

        return  abs;

    }

    public static float getScalarProduct(Vector2f vectorA, Vector2f vectorB) {

        if(vectorA == null) vectorA = new Vector2f();
        if(vectorB == null) vectorB = new Vector2f();

        float scalarProduct;

        scalarProduct = vectorA.x * vectorB.x + vectorA.y * vectorB.y;

        return scalarProduct;

    }

}
