package mEngine.util.threading;

public class AsyncHelper {

    //Run a method asynchronously
    public static void runAsyncTask(final IAsyncTask asyncTask) {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                asyncTask.run();
            }
        };
        new Thread(task).run();

    }

}
