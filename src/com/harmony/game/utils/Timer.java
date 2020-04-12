package com.harmony.game.utils;

public class Timer {

    private double stopTime;
    private boolean isActive = false;

    public void delay(int delayMillis) {
        stopTime = System.currentTimeMillis() + delayMillis;
        isActive = true;
    }

    public boolean done() {
        if(System.currentTimeMillis() >= stopTime) {
            isActive = false;
            return true;
        }

        return false;
    }

    public boolean isActive() { return isActive; }

}
