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

public class AudioSource {

    Vector3f position = new Vector3f();
    Vector3f rotation = new Vector3f();

    int buffer;
    int source;

    FloatBuffer sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    FloatBuffer sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();

    FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[]{0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f}).rewind();

    public AudioSource(GameObject source, GameObject listener) throws LWJGLException{

        try {

            AL.create();

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

        alGetError();
        if(loadALData() == AL_FALSE) throw new LWJGLException();

        position = source.position;
        rotation = source.rotation;

        listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[]{listener.position.x, listener.position.y, listener.position.z}).rewind();
        listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
        listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[]{0, 0, -1, 0.0f, 1.0f, 0.0f}).rewind();

        setListenerValues();

    }

    public void play() { alSourcePlay(source); }
    public void pause() { alSourcePause(source); }
    public void stop() { alSourceStop(source); }
    public void close() { killALData(); }

    int loadALData() {

        WaveData waveData = null;

        try {

            FileInputStream stream = new FileInputStream(new File("res/assets/sounds/test.wav"));
            waveData = WaveData.create(new BufferedInputStream(stream));

        }
        catch (FileNotFoundException e) {

            e.printStackTrace();
            System.exit(1);

        }
        buffer = alGenBuffers();
        if(alGetError() != AL_NO_ERROR) return AL_FALSE;
        alBufferData(buffer, waveData.format, waveData.data, waveData.samplerate);
        waveData.dispose();

        source = alGenSources();
        if(alGetError() != AL_NO_ERROR) return  AL_FALSE;

        alSourcei(source, AL_BUFFER, buffer);
        alSourcef(source, AL_PITCH, 1.0f);
        alSourcef(source, AL_GAIN, 1.0f);
        alSource(source, AL_POSITION, sourcePos);
        alSource(source, AL_VELOCITY, sourceVel);

        if(alGetError() == AL_NO_ERROR) return  AL_TRUE;
        else return AL_FALSE;

    }

    void setListenerValues() {

        alListener(AL_POSITION, listenerPos);
        alListener(AL_VELOCITY, listenerVel);
        alListener(AL_ORIENTATION, listenerOri);

    }

    void killALData() {

        alDeleteSources(source);
        alDeleteBuffers(buffer);
        AL.destroy();

    }

}
