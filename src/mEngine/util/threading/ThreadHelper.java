package mEngine.util.threading;

public class ThreadHelper {

    //Run a method asynchronously
    public static void startThread(Thread thread) {

        new java.lang.Thread(thread).start();

    }

}
