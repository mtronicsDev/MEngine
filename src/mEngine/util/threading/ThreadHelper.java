package mEngine.util.threading;

public class ThreadHelper {

    //Run a method asynchronously
    public static void startThread(Runnable thread) {

        new java.lang.Thread(thread).start();

    }

}
