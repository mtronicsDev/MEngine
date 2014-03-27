package mEngine.gameObjects.components;

import mEngine.gameObjects.GameObject;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;

public class AudioListener extends Component {

    FloatBuffer listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    FloatBuffer listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
    FloatBuffer listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[]{0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f}).rewind();

    public AudioListener(GameObject listener) {

        listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{listener.position.x, listener.position.y, listener.position.z}).rewind();
        listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
        listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[]{listener.percentRotation.x, listener.percentRotation.y, listener.percentRotation.z, 0.0f, 1.0f, 0.0f}).rewind();

    }

    public void onUpdate(GameObject obj) {

        alListener(AL_POSITION, (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{obj.position.x, obj.position.y, obj.position.z}).rewind());
        alListener(AL_VELOCITY, listenerVel);
        alListener(AL_ORIENTATION, (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[]{
                obj.percentRotation.x, obj.percentRotation.y, obj.percentRotation.z,
                0.0f, 1.0f, 0.0f}).rewind());

    }

}
