package mEngine.graphics.renderable.animations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Animation {

    protected List<KeyFrame> keyFrames;
    private int currentKeyFrameIndex;
    private boolean stopAfterOneCycle;
    private boolean running = false;

    public Animation(KeyFrame[] keyFrames, boolean stopAfterOneCycle) {

        this.keyFrames = new ArrayList<KeyFrame>();
        Collections.addAll(this.keyFrames, keyFrames);
        if (!this.keyFrames.isEmpty()) currentKeyFrameIndex = 0;
        this.stopAfterOneCycle = stopAfterOneCycle;
        start();

    }

    public void start() {

        running = true;

    }

    public void pause() {

        running = false;

    }

    public void stop() {

        if (!keyFrames.isEmpty()) currentKeyFrameIndex = 0;
        running = false;

    }

    public KeyFrame getCurrentKeyFrame() {

        if (running) {

            if (!keyFrames.get(currentKeyFrameIndex).isActive()) keyFrames.get(currentKeyFrameIndex).setActive();
            if (keyFrames.get(currentKeyFrameIndex).isDone()) {

                if (!stopAfterOneCycle) {
                    if (currentKeyFrameIndex == keyFrames.size() - 1) currentKeyFrameIndex = 0;
                    else currentKeyFrameIndex++;
                } else if (currentKeyFrameIndex < keyFrames.size() - 1) currentKeyFrameIndex++;

            }

        }

        return keyFrames.get(currentKeyFrameIndex);

    }

}

