package com.harmony.game.graphics;

import java.awt.image.BufferedImage;

public class Animation {

    private Sprite sprite;
    private int currentFrame = 0;

    private double stopTime;

    private int animation;

    private boolean delay = false;

    public Animation(Sprite sprite) { this.sprite = sprite; }

    public void setCurrentFrame(int frame) { this.currentFrame = frame; }

    public BufferedImage animate(int animation, int delayMillis, int frameCount) {
        if(this.animation != animation) {
            delay = false;
            currentFrame = 0;
        }

        this.animation = animation;

        if(delayMillis == -1) return sprite.getSprite(0, animation);

        if(delay) {
            stopTime = System.currentTimeMillis() + delayMillis;
            delay = false;
        }

        if(System.currentTimeMillis() < stopTime) return sprite.getSprite(currentFrame, animation);

        if(currentFrame >= sprite.getNumColumns() - 1) currentFrame = 0;
        else currentFrame++;

        if(currentFrame >= frameCount) currentFrame = 0;

        delay = true;

        return sprite.getSprite(currentFrame, animation);
    }

    public BufferedImage animate(int animation, int delayMillis) {
        if(this.animation != animation) {
            delay = false;
            currentFrame = 0;
        }

        this.animation = animation;

        if(delayMillis == -1) return sprite.getSprite(0, animation);

        if(delay) {
            stopTime = System.currentTimeMillis() + delayMillis;
            delay = false;
        }

        if(System.currentTimeMillis() < stopTime) return sprite.getSprite(currentFrame, animation);

        if(currentFrame >= sprite.getNumColumns() - 1) currentFrame = 0;
        else currentFrame++;

        delay = true;

        return sprite.getSprite(currentFrame, animation);
    }

}
