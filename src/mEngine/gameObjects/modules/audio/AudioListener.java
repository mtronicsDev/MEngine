package mEngine.gameObjects.modules.audio;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class AudioListener {

    private FloatBuffer position = BufferUtils.createFloatBuffer(3);
    private FloatBuffer velocity = BufferUtils.createFloatBuffer(3);
    private FloatBuffer rotation = BufferUtils.createFloatBuffer(6); //Last 3 values represent the "up" vector

}
