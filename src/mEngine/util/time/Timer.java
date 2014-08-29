package mEngine.util.time;

public class Timer {

    private int tickTime;
    private long startTime;
    private boolean running = false;

    public Timer(int tickTime) {

        this.tickTime = tickTime;

    }

    public void start() {

        startTime = TimeHelper.getTime();
        running = true;

    }

    public boolean isRunning() {

        return running;

    }

    public boolean isDone() {

        return tickTime >= TimeHelper.getTime() - startTime;

    }

}
