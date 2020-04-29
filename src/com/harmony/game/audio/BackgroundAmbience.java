package com.harmony.game.audio;

import com.harmony.game.state.SettingsState;

public class BackgroundAmbience {

    public static final int CAVE_AMBIENCE = 0;
    public static final int OUTSIDE_AMBIENCE = 1;

    private static final AudioClip caveAmbience = new AudioClip("/audio/ambience/cave_ambience.wav");
    private static final AudioClip outsideAmbience = new AudioClip("/audio/ambience/outside_ambience.wav");

    public static void playBackgroundAudio(int audio) {
        if(!SettingsState.isBackgroundMusic) return;
        assert getAudio(audio) != null;
        getAudio(audio).loop();
    }

    public static void stopBackgroundAudio(int audio) {
        assert getAudio(audio) != null;
        getAudio(audio).stop();
    }

    public static void stopAllBackground() {
        caveAmbience.stop();
        outsideAmbience.stop();
    }

    public static void cleanUp() {
        caveAmbience.close();
        outsideAmbience.close();
    }

    private static AudioClip getAudio(int audio) {
        switch (audio) {
            case CAVE_AMBIENCE:     return caveAmbience;
            case OUTSIDE_AMBIENCE:  return outsideAmbience;
        }

        return null;
    }

}
