package com.harmony.game.animation.scene.chapter1;

import com.harmony.game.Game;
import com.harmony.game.animation.Animation;
import com.harmony.game.animation.scene.Scene;
import com.harmony.game.audio.AudioClip;
import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.utils.GUI;
import com.harmony.game.utils.ImageUtils;
import com.harmony.game.utils.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chapter1Scene1 extends Scene {

    private BufferedImage background;

    private Sprite darkLord;
    private Sprite explosionOverlay;

    private Animation darkLordAnimation;
    private Animation crystalsAnimation;
    private Animation explosionAnimation;

    private AudioClip explosionAudio;

    private boolean explosion = false;
    private boolean doneExplosion = false;

    private AudioClip line1;
    private AudioClip line2;
    private AudioClip line3;
    private AudioClip line4;
    private AudioClip line5;
    private AudioClip line6;
    private AudioClip line7;

    private Timer timer = new Timer();

    private Color overlayColor = new Color(182, 128, 246, 25);

    private int currentLine = 0;

    @Override
    public void onCreate() {
        line1 = new AudioClip("/scenes/chapter1/line1.wav");
        line2 = new AudioClip("/scenes/chapter1/line2.wav");
        line3 = new AudioClip("/scenes/chapter1/line3.wav");
        line4 = new AudioClip("/scenes/chapter1/line4.wav");
        line5 = new AudioClip("/scenes/chapter1/line5.wav");
        line6 = new AudioClip("/scenes/chapter1/line6.wav");
        line7 = new AudioClip("/scenes/chapter1/line7.wav");

        background = ImageUtils.loadImage("/scenes/chapter1/dark_lords_dungeon.png");

        darkLord = new Sprite("/entity/enemy/dark-lord.png", 64, 64);
        darkLordAnimation = new Animation(darkLord);

        crystalsAnimation = new Animation(GUI.blueCrystals);

        explosionOverlay = new Sprite(ImageUtils.loadImage("/scenes/chapter1/explosion-overlay.png"), 640, 360);
        explosionAnimation = new Animation(explosionOverlay);

        explosionAudio = new AudioClip("/audio/sfx/explosion-subtle.wav");

        timer.delay(700);
    }

    @Override
    public void update() {
        if(!timer.done() && !doneExplosion) return;

        if(!line1.isPlaying() && currentLine == 0) { line1.play(); currentLine++; }

        if(!line1.isPlaying() && currentLine == 1) {
            line2.play();
            line1.close();
            currentLine++;
        }

        if(!line2.isPlaying() && currentLine == 2) {
            line3.play();
            line2.close();
            currentLine++;
        }

        if(!line3.isPlaying() && currentLine == 3) {
            line4.play();
            line3.close();
            currentLine++;
        }

        if(!line4.isPlaying() && currentLine == 4) {
            line5.play();
            line4.close();
            currentLine++;
        }

        if(!line5.isPlaying() && currentLine == 5) {
            if(!explosion && !doneExplosion) explosion = true;
            if(!doneExplosion) return;
            if(!timer.done()) return;
            line6.play();
            line5.close();
            currentLine++;
        }

        if(!line6.isPlaying() && currentLine == 6) {
            line7.play();
            line6.close();
            currentLine++;
        }

        if(!line7.isPlaying() && currentLine == 7) {
            line7.close();
            isDone = true;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if(doneExplosion) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Display.width, Display.height);
            super.makeCinematic(g);
            return;
        }

        // Background
        g.drawImage(background, 0, 0, null);

        // Dark Lord
        g.drawImage(darkLordAnimation.animate(9, 2000, 3), Display.width / 2 - 32, 150, null);

        // Crystals
        crystalsAnimation.animate(0, 200);
        g.drawImage(GUI.greenCrystals.getSprite(crystalsAnimation.getCurrentFrame(), 0), Display.width / 2 + 48, Display.height / 2 - 70, null);
        g.drawImage(GUI.orangeCrystals.getSprite(crystalsAnimation.getCurrentFrame(), 0), Display.width / 2 + 16, Display.height / 2 - 70, null);
        g.drawImage(GUI.blueCrystals.getSprite(crystalsAnimation.getCurrentFrame(), 0), Display.width / 2 - 16, Display.height / 2 - 70, null);
        g.drawImage(GUI.yellowCrystals.getSprite(crystalsAnimation.getCurrentFrame(), 0), Display.width / 2 - 48, Display.height / 2 - 70, null);
        g.drawImage(GUI.greyCrystals.getSprite(crystalsAnimation.getCurrentFrame(), 0), Display.width / 2 - 80, Display.height / 2 - 70, null);

        // Explosion
        if(explosion) {
            if(explosionAnimation.getCurrentFrame() == 0) explosionAudio.play();

            if(explosionAnimation.getCurrentFrame() == 4) {
                explosion = false;
                doneExplosion = true;
                timer.delay(2500);
            }

            g.drawImage(explosionAnimation.animate(0, 10), 0, 0, Display.width, Display.height, null);
        }

        // Overlay
        g.setColor(overlayColor);
        g.fillRect(0, 0, Display.width, Display.height);

        super.makeCinematic(g);
    }

    @Override
    public void onDestroy() {
        background.flush();
        explosionAudio.close();
    }

}
