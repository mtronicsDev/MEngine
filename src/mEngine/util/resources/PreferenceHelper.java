package mEngine.util.resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PreferenceHelper {

    private static Properties properties = new Properties();

    public static void loadPreferences(String fileName) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(ResourceHelper.getResource(fileName, ResourceHelper.RES_PREFERENCE)));
            properties.load(reader);
            reader.close();

        } catch (IOException e) {

            e.printStackTrace();
            System.exit(1);

        }

    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }

    public static boolean getBoolean(String key) {

        String value = getValue(key);
        return value.toLowerCase().equals("true") || value.equals("1");

    }

    public static int getInteger(String key) {
        return Integer.valueOf(getValue(key));
    }

    public static float getFloat(String key) {
        return Float.valueOf(getValue(key));
    }

}
