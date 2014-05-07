package mEngine.gameObjects.components.interaction.methods;

public abstract class AsyncMethod extends InteractionMethod implements Runnable {

    public void run() { interact(); }

    public abstract void interact();

}
