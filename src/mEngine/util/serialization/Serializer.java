package mEngine.util.serialization;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.util.resources.ResourceHelper;

import java.io.*;
import java.util.ArrayList;

public class Serializer {

    public static boolean isSerializing = false;

    private static void serialize(GameObject obj) {

        String saveFileName = String.valueOf(obj.getUuid());
        obj.save();

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

    private static void deSerialize(String fileName) {

        GameObject object = null;

        try {
            File file = ResourceHelper.getResource(fileName, ResourceHelper.RES_SAVE);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            object = (GameObject) objectInputStream.readObject();
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

        object.load();
        ObjectController.gameObjects.add(object);

    }

    public static void serializeAll() {

        isSerializing = true;

        for (int i = 0; i < ObjectController.gameObjects.size(); i++) {

            serialize(ObjectController.getGameObject(i));

        }

    }

    public static void deSerializeAll() {

        ObjectController.gameObjects = new ArrayList<GameObject>();
        File saveDirectory = new File("res/saves/");
        String[] saves = saveDirectory.list();
        for (String save : saves) {

            save = save.replace(".mso", "");
            deSerialize(save);

        }

        isSerializing = false;

    }

}
