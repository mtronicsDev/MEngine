package mEngine.gameObjects.modules.interaction;

public abstract class AsyncInteraction extends Interaction implements Runnable {

    public void run() {
        interact();
    }

    public abstract void interact();

}
