package mEngine.gameObjects.components.audio;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import org.lwjgl.BufferUtils;

import java.io.Serializable;
import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;

public class AudioListener extends Component implements Serializable {

    FloatBuffer listenerPos;
    FloatBuffer listenerVel;
    FloatBuffer listenerOri;

    @Override
    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{parent.position.x, parent.position.y, parent.position.z}).rewind();
        listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
        listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[]{parent.percentRotation.x, parent.percentRotation.y, parent.percentRotation.z, 0.0f, 1.0f, 0.0f}).rewind();

    }

    public void onUpdate() {

        alListener(AL_POSITION, (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{parent.position.x, parent.position.y, parent.position.z}).rewind());
        alListener(AL_VELOCITY, listenerVel);
        alListener(AL_ORIENTATION, (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[]{
                parent.percentRotation.x, parent.percentRotation.y, parent.percentRotation.z,
                0.0f, 1.0f, 0.0f}).rewind());

    }

}
