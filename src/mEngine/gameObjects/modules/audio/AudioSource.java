package mEngine.gameObjects.modules.audio;

import mEngine.gameObjects.modules.Module;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class AudioSource extends Module {

    private FloatBuffer position = BufferUtils.createFloatBuffer(3);
    private FloatBuffer velocity = BufferUtils.createFloatBuffer(3);

}
