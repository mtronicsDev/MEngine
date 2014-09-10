package mEngine.gameObjects.modules.interaction.methods;

public abstract class AsyncMethod extends InteractionMethod implements Runnable {

    public void run() {
        interact();
    }

    public abstract void interact();

}
