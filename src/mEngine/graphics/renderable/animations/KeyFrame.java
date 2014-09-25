/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.graphics.renderable.animations;

import mEngine.util.time.Timer;

public abstract class KeyFrame {

    protected Object data; //Can be a texture, list of vertices or something else
    private int delay; //In milliseconds
    private Timer delayTimer;

    /**
     * Create a keyframe for use in animations
     * @param delay The time this keyframe will be active once activated
     * @param data The keyframe's data (e.g. texture, position or color)
     */
    KeyFrame(int delay, Object data) {

        this.delay = delay;
        this.data = data;
        delayTimer = new Timer(delay);

    }

    /**
     * Called by the animation the keyframe is part of.
     * The keyframe deactivates automatically after the specified delay time
     */
    void setActive() {

        delayTimer.start();

    }

    /**
     * Used by the animation this keyframe is part of to determine if the frame is done being active
     * @return True if it's done, false if not
     */
    boolean isDone() {

        return delayTimer.isDone();

    }

    /**
     * Used by the animation this keyframe is part of to determine if the frame is still active
     * @return True if active, false if not
     */
    boolean isActive() {

        return delayTimer.isRunning();

    }

    /**
     * Used by the animation this keyframe is part of to determine the data attached to the keyframe
     * @return The keyframe's data
     */
    public abstract Object getData();

}
