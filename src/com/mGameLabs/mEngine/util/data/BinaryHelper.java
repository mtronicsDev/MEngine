package com.mGameLabs.mEngine.util.data;

public class BinaryHelper {

    public static String convertToBinaryString(boolean b) {

        String binaryCode;

        if (b) binaryCode = "1";

        else binaryCode = "0";

        return binaryCode;

    }

    public static int convertToBinaryInteger(boolean b) {

        int binaryCode;

        if (b) binaryCode = 1;

        else binaryCode = 0;

        return binaryCode;

    }

}
