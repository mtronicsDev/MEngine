package mEngine.gameObjects.components.audio;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.util.audio.AudioHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;

public class AudioSource extends Component {

    public int buffer;
    public int source;
    public FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    public FloatBuffer sourceVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    Vector3f position = new Vector3f();
    Vector3f rotation = new Vector3f();

    public AudioSource(GameObject source, String fileName) {

        this(source, fileName, false);

    }

    public AudioSource(GameObject source, String fileName, boolean addedAsLast) {

        super(addedAsLast);

        alGetError();
        if (AudioHelper.loadALData(this, fileName) == AL_FALSE) return;

        position = source.position;
        rotation = source.rotation;

    }

    //TODO: Add an update method to refresh position and velocity

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
