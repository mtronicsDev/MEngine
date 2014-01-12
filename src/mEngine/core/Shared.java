package mEngine.core;

import mEngine.util.PreferenceHelper;
import org.lwjgl.util.vector.Vector4f;

public class Shared {

    public static Vector4f skyColor = PreferenceHelper.getRgbaValue("skyColor");

}
