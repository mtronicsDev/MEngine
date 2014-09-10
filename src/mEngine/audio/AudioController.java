package mEngine.audio;

import mEngine.util.resources.ResourceHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import java.nio.IntBuffer;

public class AudioController {

    private static IntBuffer soundBuffer = BufferUtils.createIntBuffer(1);
    private static IntBuffer sourceBuffer = BufferUtils.createIntBuffer(1);

    public static void initialize() {
        try {
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.out.println(AL10.alGetError());
            System.exit(1);
        }
    }

    public static int createSource(String audioFile) {
        AL10.alGenBuffers(soundBuffer);

        //e.g. for low memory errors
        if(AL10.alGetError() != AL10.AL_NO_ERROR) return AL10.AL_FALSE;

        WaveData waveData = WaveData.create(ResourceHelper.getResource(audioFile, ResourceHelper.RES_SOUND).getPath());
        AL10.alBufferData(soundBuffer.get(0), waveData.format, waveData.data, waveData.samplerate);
        waveData.dispose();

        AL10.alGenSources(sourceBuffer);

        //e.g. for low memory errors
        if(AL10.alGetError() != AL10.AL_NO_ERROR) return AL10.AL_FALSE;

        AL10.alSourcei(sourceBuffer.get(0), AL10.AL_BUFFER, soundBuffer.get(0));
        AL10.alSourcef(sourceBuffer.get(0), AL10.AL_PITCH, 1);
        AL10.alSourcef(sourceBuffer.get(0), AL10.AL_GAIN, 1);
        AL10.alSource(sourceBuffer.get(0), AL10.AL_POSITION, BufferUtils.createFloatBuffer(3));
        AL10.alSource(sourceBuffer.get(0), AL10.AL_VELOCITY, BufferUtils.createFloatBuffer(3));

        if(AL10.alGetError() == AL10.AL_NO_ERROR) return AL10.AL_TRUE;
        else return AL10.AL_FALSE;
    }

    public static void createListener() {
        AL10.alListener(AL10.AL_POSITION, BufferUtils.createFloatBuffer(3));
        AL10.alListener(AL10.AL_VELOCITY, BufferUtils.createFloatBuffer(3));
        AL10.alListener(AL10.AL_ORIENTATION, BufferUtils.createFloatBuffer(6));
    }

    public static void disposeAudioData() {
        AL10.alDeleteBuffers(soundBuffer);
        AL10.alDeleteSources(sourceBuffer);
    }

}
