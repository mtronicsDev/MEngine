package mEngine.util.debug;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MEngineLogger {

    public static final Logger LOGGER = Logger.getLogger(MEngineLogger.class.getName());

    public static void initialize() {

        //Add handlers and all that jazz

    }

    public static void log(String message) {

        log(message, Level.INFO);

    }

    public static void log(String message, Level level) {

        LOGGER.log(level, message);

    }

}
