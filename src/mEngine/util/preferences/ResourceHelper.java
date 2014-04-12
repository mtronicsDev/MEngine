package mEngine.util.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResourceHelper {

    public static final int RES_GENERIC = 0;

    public static final int RES_ASSET = 1;
    public static final int RES_PREFERENCE = 2;
    public static final int RES_WORLD = 3;

    public static final int RES_MATERIAL = 4;
    public static final int RES_MODEL = 5;
    public static final int RES_SOUND = 6;
    public static final int RES_TEXTURE = 7;

    private static List<String> paths = new ArrayList<String>();

    public static void initialize() {

        paths.add("res/"); //0

        paths.add(paths.get(0) + "assets/"); //1
        paths.add(paths.get(0) + "preferences/"); //2
        paths.add(paths.get(0) + "worlds/"); //3

        paths.add(paths.get(1) + "materials/"); //4
        paths.add(paths.get(1) + "models/"); //5
        paths.add(paths.get(1) + "sounds/"); //6
        paths.add(paths.get(1) + "textures/"); //7

    }

    public static File getResource(String fileName, int type) {

        String filePath;

        switch (type) {

            default:
                filePath = paths.get(type) + fileName;
                break;
            case 2:
                filePath = paths.get(type) + fileName + ".mmp";
                break;
            case 3:
                filePath = paths.get(type) + fileName + ".txt";
                break;
            case 4:
                filePath = paths.get(type) + fileName + ".mtl";
                break;
            case 5:
                filePath = paths.get(type) + fileName + ".obj";
                break;
            case 6:
                filePath = paths.get(type) + fileName + ".wav";
                break;
            case 7:
                filePath = paths.get(type) + fileName + ".png";
                break;

        }

        return new File(filePath);

    }

}
