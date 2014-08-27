package com.mGameLabs.mEngine.graphics.renderable.animations;

import com.mGameLabs.mEngine.util.time.Timer;

public abstract class KeyFrame {

    protected Object data; //Can be a texture, list of vertices or something else
    private int delay; //In milliseconds
    private Timer delayTimer;

    KeyFrame(int delay, Object data) {

        this.delay = delay;
        this.data = data;
        delayTimer = new Timer(delay);

    }

    void setActive() {

        delayTimer.start();

    }

    boolean isDone() {

        return delayTimer.isDone();

    }

    boolean isActive() {

        return delayTimer.isRunning();

    }

    public abstract Object getData();

}
