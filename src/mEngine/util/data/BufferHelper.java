package mEngine.util.data;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferHelper {

    public static IntBuffer addToBuffer(IntBuffer buffer, int value) {
        IntBuffer newBuffer = BufferUtils.createIntBuffer(buffer.capacity() + 1);
        newBuffer.put(buffer);
        newBuffer.put(value);
        return newBuffer;
    }

    public static FloatBuffer addToBuffer(FloatBuffer buffer, int value) {
        FloatBuffer newBuffer = BufferUtils.createFloatBuffer(buffer.capacity() + 1);
        newBuffer.put(buffer);
        newBuffer.put(value);
        return newBuffer;
    }

    public static FloatBuffer toBuffer(Vector3f vector) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
        vector.store(buffer);
        return buffer;
    }

    public static FloatBuffer toBuffer(Vector3f[] vectors) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vectors.length * 3);
        for(Vector3f vector : vectors) vector.store(buffer);
        return buffer;
    }

}
