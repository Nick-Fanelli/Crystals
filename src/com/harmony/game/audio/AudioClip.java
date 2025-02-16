package com.harmony.game.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioClip {

    private Clip clip = null;

    public AudioClip(String path) {
        try {
            System.out.println("Loading Audio Clip: " + path);

            InputStream audioSrc = AudioClip.class.getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(), false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);

            clip = AudioSystem.getClip();
            clip.open(dais);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Crystals: Error loading audio clip " + path);
            e.printStackTrace();
        }
    }

    public void setGain(float gain) { ((FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(gain); }
    public boolean isActive() { return clip.isRunning(); }
    public boolean isPlaying() { return clip.isActive(); }
    public Clip getClip() { return clip; }

    public void play() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);

        int i = 0;
        while (!clip.isRunning() && i < 10) {
            clip.start();
            i++;
        }
    }

    public void forcePlay(int num) {
        if(clip == null) return;
        clip.loop(num);
        play();
    }

    public void stop() {
        if (clip.isRunning()) clip.stop();
    }

    public void close() {
        stop();
        clip.drain();
        clip.close();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }
}
