package com.harmony.game.audio;

public class BackgroundMusic {

    public static final int CAVE_BACKGROUND_AUDIO = 0;
    public static final AudioClip caveBackground = new AudioClip("/audio/cave_ambience.wav");

    public static void playBackgroundAudio(int audio) {
        if(audio == CAVE_BACKGROUND_AUDIO) caveBackground.loop();
    }

    public static void stopBackgroundAudio(int audio) {
        if(audio == CAVE_BACKGROUND_AUDIO) caveBackground.stop();
    }

}
