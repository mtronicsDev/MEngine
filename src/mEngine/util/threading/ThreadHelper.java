package mEngine.util.threading;

import java.util.ArrayList;
import java.util.List;

public class ThreadHelper {

    private static List<Thread> threads = new ArrayList<Thread>();

    //Run a method asynchronously
    public static void startThread(Runnable thread) {

        //Adds the desired thread to the list and starts it
        threads.add(new Thread(thread));
        threads.get(threads.size() - 1).start();

    }

    public static void stopAllThreads() {

        for (Thread thread : threads) {

            thread.interrupt();

        }

    }

}
