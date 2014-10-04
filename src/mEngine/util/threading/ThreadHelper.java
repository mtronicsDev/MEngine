/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.threading;

import java.util.ArrayList;
import java.util.List;

public class ThreadHelper {

    private static List<Thread> threads = new ArrayList<Thread>();

    /**
     * Runs a thread asynchronously
     *
     * @param thread The thread (Runnable) to run
     */
    public static void startThread(Runnable thread) {

        //Adds the desired thread to the list and starts it
        Thread newThread = new Thread(thread);
        threads.add(newThread);
        newThread.start();

    }

    /**
     * Stops all threads that were run via startThread().
     * Note that all mengine threads will be stopped as well
     */
    public static void stopAllThreads() {

        for (Thread thread : threads) {

            thread.interrupt();

        }

    }

}
