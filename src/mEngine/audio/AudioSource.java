package mEngine.audio;

import mEngine.interactive.gameObjects.GameObject;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;

public class AudioSource {

    Vector3f position = new Vector3f();
    Vector3f rotation = new Vector3f();

    int buffer;
    int source;

    FloatBuffer sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    FloatBuffer sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    public AudioSource(GameObject source, String fileName) throws LWJGLException{

        alGetError();
        if(AudioController.loadALData(this, fileName) == AL_FALSE) throw new LWJGLException();

        position = source.position;
        rotation = source.rotation;

    }

    public void play() { alSourcePlay(source); }
    public void pause() { alSourcePause(source); }
    public void stop() { alSourceStop(source); }
    public void close() {

        alDeleteSources(source);
        alDeleteBuffers(buffer);

    }

}
