package mEngine.gameObjects.components;

import mEngine.audio.AudioController;
import mEngine.gameObjects.GameObject;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;

public class AudioSource extends Component {

    Vector3f position = new Vector3f();
    Vector3f rotation = new Vector3f();

    public int buffer;
    public int source;

    public FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    public FloatBuffer sourceVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    public AudioSource(GameObject source, String fileName) {

        alGetError();
        if (AudioController.loadALData(this, fileName) == AL_FALSE) return;

        position = source.position;
        rotation = source.rotation;

    }

    public void play() {
        alSourcePlay(source);
    }

    public void pause() {
        alSourcePause(source);
    }

    public void stop() {
        alSourceStop(source);
    }

    public void close() {

        alDeleteSources(source);
        alDeleteBuffers(buffer);

    }

}
