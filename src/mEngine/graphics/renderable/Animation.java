package mEngine.graphics.renderable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Animation {

    protected List<KeyFrame> keyframes;
    private int currentKeyFrameIndex;
    private boolean stopAfterOneCycle;
    private boolean running = false;

    public Animation(KeyFrame[] keyframes, boolean stopAfterOneCycle) {

        this.keyframes = new ArrayList<KeyFrame>();
        Collections.addAll(this.keyframes, keyframes);
        if (!this.keyframes.isEmpty()) currentKeyFrameIndex = 0;
        this.stopAfterOneCycle = stopAfterOneCycle;

    }

    public void start() {

        running = true;

    }

    public void pause() {

        running = false;

    }

    public void stop() {

        if (!keyframes.isEmpty()) currentKeyFrameIndex = 0;
        running = false;

    }

    public KeyFrame getCurrentKeyFrame() {

        if (running) {

            if (!keyframes.get(currentKeyFrameIndex).isActive()) keyframes.get(currentKeyFrameIndex).setActive();
            if (keyframes.get(currentKeyFrameIndex).isDone()) {

                if (!stopAfterOneCycle) {
                    if (currentKeyFrameIndex == keyframes.size() - 1) currentKeyFrameIndex = 0;
                    else currentKeyFrameIndex++;
                } else if (currentKeyFrameIndex < keyframes.size() - 1) currentKeyFrameIndex++;

            }

        }

        return keyframes.get(currentKeyFrameIndex);

    }

}

