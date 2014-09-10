package mEngine.audio;

import mEngine.MEngineException;
import mEngine.gameObjects.modules.audio.AudioListener;
import mEngine.gameObjects.modules.audio.AudioSource;
import mEngine.gameObjects.modules.physics.MovementModule;
import mEngine.util.data.BufferHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static mEngine.util.resources.ResourceHelper.RES_SOUND;
import static mEngine.util.resources.ResourceHelper.getResource;
import static org.lwjgl.openal.AL10.*;

public class AudioController {

    private static IntBuffer soundBuffer = BufferUtils.createIntBuffer(0);
    private static IntBuffer sourceBuffer = BufferUtils.createIntBuffer(0);
    private static Map<AudioSource, Integer> sourceMap = new HashMap<AudioSource, Integer>();

    public static void initialize() {
        try {
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.out.println(alGetError());
            System.exit(1);
        }
    }

    public static void addSource(AudioSource source) throws MEngineException {
        //Enlargen buffers for the extra audio source
        soundBuffer = BufferHelper.addToBuffer(soundBuffer, 0);
        sourceBuffer = BufferHelper.addToBuffer(sourceBuffer, 0);

        alGenBuffers(soundBuffer);

        //e.g. for low memory errors
        if(alGetError() != AL_NO_ERROR)
            throw new MEngineException("Could not create audio buffers.");

        WaveData waveData = null;

        try {
            FileInputStream stream = new FileInputStream(getResource(source.getAudioFileName(), RES_SOUND));
            waveData = WaveData.create(new BufferedInputStream(stream));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        alBufferData(soundBuffer.position(), waveData.format, waveData.data, waveData.samplerate);
        waveData.dispose();

        //e.g. for low memory errors
        if(alGetError() != AL_NO_ERROR)
            throw new MEngineException("Could not load audio file '" + source.getAudioFileName() + "'.");

        alGenSources(sourceBuffer);

        int index = sourceBuffer.position() - 1; //Index of the current audioSource
        alSourcei(sourceBuffer.get(index), AL_BUFFER, soundBuffer.get(index));
        alSourcef(sourceBuffer.get(index), AL_PITCH, source.pitch);
        alSourcef(sourceBuffer.get(index), AL_GAIN, source.gain);
        alSource(sourceBuffer.get(index), AL_POSITION, (FloatBuffer) BufferHelper.toBuffer(source.parent.position).rewind());

        MovementModule module = (MovementModule) source.parent.getModule(MovementModule.class);
        alSource(sourceBuffer.get(index), AL_VELOCITY, (FloatBuffer) BufferHelper.toBuffer(module.speed).rewind());

        if(alGetError() != AL_NO_ERROR)
            throw new MEngineException("Could not create audio source.");

        sourceMap.put(source, index);
    }

    public static void updateSource(AudioSource source) {
        int index = sourceMap.get(source);
        alSourcei(sourceBuffer.get(index), AL_BUFFER, soundBuffer.get(index));
        alSourcef(sourceBuffer.get(index), AL_PITCH, source.pitch);
        alSourcef(sourceBuffer.get(index), AL_GAIN, source.gain);
        alSource(sourceBuffer.get(index), AL_POSITION, (FloatBuffer) BufferHelper.toBuffer(source.parent.position).rewind());

        MovementModule module = (MovementModule) source.parent.getModule(MovementModule.class);
        alSource(sourceBuffer.get(index), AL_VELOCITY, (FloatBuffer) BufferHelper.toBuffer(module.speed).rewind());
    }

    public static void updateListener(AudioListener listener) {
        alListener(AL_POSITION, (FloatBuffer) BufferHelper.toBuffer(listener.parent.position).rewind());

        MovementModule module = (MovementModule) listener.parent.getModule(MovementModule.class);
        alListener(AL_VELOCITY, (FloatBuffer) BufferHelper.toBuffer(module.speed).rewind());

        Vector3f[] orientationVectors = new Vector3f[]{
                listener.parent.rotation,
                new Vector3f(0, 1, 0) //"Up" vector
        };
        alListener(AL_ORIENTATION, (FloatBuffer) BufferHelper.toBuffer(orientationVectors).rewind());
    }

    public static void play(AudioSource source) {
        alSourcePlay(sourceBuffer.get(sourceMap.get(source)));
    }

    public static void pause(AudioSource source) {
        alSourcePause(sourceBuffer.get(sourceMap.get(source)));
    }

    public static void stop(AudioSource source) {
        alSourceStop(sourceBuffer.get(sourceMap.get(source)));
    }

    public static void disposeAudioData() {
        alDeleteBuffers(soundBuffer);
        alDeleteSources(sourceBuffer);
        AL.destroy();
    }

}
