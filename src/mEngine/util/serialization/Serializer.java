package mEngine.util.serialization;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.util.resources.ResourceHelper;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class Serializer {

    public static boolean isSerializing = false;

    public static void serialize() {

        isSerializing = true;

        SaveObject obj = new SaveObject(ObjectController.gameObjects);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String saveFileName = format.format(date);
        for (int i = 0; i < ObjectController.gameObjects.size(); i++) {

            ObjectController.getGameObject(i).save();

        }

        try {
            File file = ResourceHelper.getResource(saveFileName, ResourceHelper.RES_SAVE);
            if (!file.exists()) //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deSerialize(String fileName) {

        SaveObject object = null;

        try {
            File file = ResourceHelper.getResource(fileName, ResourceHelper.RES_SAVE);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            object = (SaveObject) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            //noinspection ResultOfMethodCallIgnored
            file.delete();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        for (GameObject obj : object.gameObjects) {

            obj.load();
            ObjectController.gameObjects.add(obj);

        }

        isSerializing = false;

    }

    public static void deSerializeLatest() {

        File file = new File("res/saves");
        File[] saves = file.listFiles();
        Arrays.sort(saves, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                long last0 = o1.lastModified(), last1 = o2.lastModified();
                if (last0 > last1) {
                    return 1;
                } else if (last0 < last1) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        if (saves != null) deSerialize(saves[0].getName().replace(".mess", ""));

    }

}
