package com.mGameLabs.mEngine.gameObjects.components.audio;

import com.mGameLabs.mEngine.gameObjects.GameObject;
import com.mGameLabs.mEngine.gameObjects.components.Component;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;

public class AudioListener extends Component {

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

        alListener(AL_POSITION, listenerPos);
        alListener(AL_VELOCITY, listenerVel);
        alListener(AL_ORIENTATION, listenerOri);

    }

    @Override
    public void onSave() {

        super.onSave();
        listenerPos = null;
        listenerVel = null;
        listenerOri = null;

    }

    @Override
    public void onLoad() {

        super.onLoad();
        listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{parent.position.x, parent.position.y, parent.position.z}).rewind();
        listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f}).rewind();
        listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[]{parent.percentRotation.x, parent.percentRotation.y, parent.percentRotation.z, 0.0f, 1.0f, 0.0f}).rewind();

    }

}
