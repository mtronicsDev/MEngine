package mEngine.audio;

import mEngine.gameObjects.GameObject;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;

public class AudioListener {

    FloatBuffer listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    FloatBuffer listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    FloatBuffer listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[]{0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f}).rewind();

    public AudioListener(GameObject listener) {

        listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{listener.position.x, listener.position.y, listener.position.z}).rewind();
        listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
        listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[]{listener.percentRotation.x, listener.percentRotation.y, listener.percentRotation.z, 0.0f, 1.0f, 0.0f}).rewind();

        setListenerValues(listenerPos, listenerVel, listenerOri);

    }

    private void setListenerValues(FloatBuffer listenerPos, FloatBuffer listenerVel, FloatBuffer listenerOri) {

        alListener(AL_POSITION, listenerPos);
        alListener(AL_VELOCITY, listenerVel);
        alListener(AL_ORIENTATION, listenerOri);

    }

}
