package mEngine.util.timing;

public class RuntimeHelper {

    public static final int TOTAL_MEMORY = 0;
    public static final int USED_MEMORY = 1;
    public static final int FREE_MEMORY = 2;

    private static Runtime runtime;

    public static void initialize() {
        runtime = Runtime.getRuntime();
    }

    public static int getMemoryStats(int mode) {

        switch (mode) {

            case TOTAL_MEMORY:
                return (int) (runtime.totalMemory() / 1024 / 1024);

            case USED_MEMORY:
                return (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024);

            case FREE_MEMORY:
                return (int) (runtime.freeMemory() / 1024 / 1024);

        }

        return 0;

    }

}
