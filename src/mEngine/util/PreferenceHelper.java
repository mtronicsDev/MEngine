package mEngine.util;

import org.lwjgl.util.vector.Vector4f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PreferenceHelper {

    private static Properties properties = new Properties();

    public static void loadPreferences(String fileName) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
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

    public static Vector4f getRgbaValue(String key) {

        String value = properties.getProperty(key);

        if(value.matches("rgba *\\( *([0-9]+), *([0-9]+), *([0-9]+), *([0-9]+) *\\)")) {

            value = value.replace("rgba(", "");
            value = value.replace(")", "");

            String[] values = value.split(", *");

            return new Vector4f(
                    Float.parseFloat(values[0]),
                    Float.parseFloat(values[1]),
                    Float.parseFloat(values[2]),
                    Float.parseFloat(values[3])
            );

        }
        else{

            return  null;

        }

    }

}
