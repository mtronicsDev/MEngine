package mEngine.graphics.renderable;

import mEngine.util.time.Timer;

public abstract class KeyFrame {

    private int delay; //In milliseconds
    private Object data; //Can be a texture, list of vertices ore something else
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

}
