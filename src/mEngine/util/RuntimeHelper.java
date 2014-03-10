package mEngine.util;

import mEngine.util.math.MathHelper;
import mEngine.util.math.graphs.Graph;
import org.lwjgl.opengl.Display;

public class RuntimeHelper {

    public static final int TOTAL_MEMORY = 0;
    public static final int USED_MEMORY = 1;
    public static final int FREE_MEMORY = 2;

    private static Runtime runtime;
    private static Graph memUsage;

    public static void initialize() { runtime = Runtime.getRuntime(); }

    public static int getMemoryStats(int mode) {

        switch (mode) {

            case TOTAL_MEMORY:
                return (int)(runtime.totalMemory() / 1024 / 1024);

            case USED_MEMORY:
                return (int)((runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024);

            case FREE_MEMORY:
                return (int)(runtime.freeMemory() / 1024 / 1024);

        }

        return 0;

    }

    public static Graph getMemoryGraph(int valueCount) {

        if(memUsage == null) memUsage = new Graph(valueCount);
        for(int i = 1; i < memUsage.getLength(); i++) {

            if(i - 1 >= 0) memUsage.updateValue(i - 1, memUsage.getX(i));

        }

        memUsage.updateValue(memUsage.getLength() - 1, (float)getMemoryStats(USED_MEMORY) / getMemoryStats(TOTAL_MEMORY) * 100);

        return memUsage;

    }

}
