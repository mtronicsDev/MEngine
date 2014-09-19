package mEngine.audio;

import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class AudioController {

    private static SoundSystem soundSystem;

    public static void initialize() {

        Class library;

        if (SoundSystem.libraryCompatible(LibraryLWJGLOpenAL.class))
            library = LibraryLWJGLOpenAL.class;
        else if (SoundSystem.libraryCompatible(LibraryJavaSound.class))
            library = LibraryJavaSound.class;
        else
            library = Library.class;

        try {
            soundSystem = new SoundSystem(library);
            SoundSystemConfig.setCodec("wav", CodecWav.class);
        } catch (SoundSystemException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public static SoundSystem getSoundSystem() {
        return soundSystem;
    }

    public static void clear() {
        soundSystem.cleanup();
    }

}
