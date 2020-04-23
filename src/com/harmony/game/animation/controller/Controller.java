package com.harmony.game.animation.controller;

import com.harmony.game.Game;
import com.harmony.game.graphics.Display;
import com.harmony.game.state.chapters.Chapter;

import java.awt.*;

public abstract class Controller {

    public static final int BAR_HEIGHT = 40;
    public static final int BAR_SPEED_MULTIPLIER = 5;

    private int currentBarHeight = 0;

    protected Chapter chapter;

    protected boolean hasControlled = false;

    public Controller(Chapter chapter) { this.chapter = chapter; }

    public void makeCinematic(Graphics2D g) {
        if(currentBarHeight < BAR_HEIGHT) currentBarHeight += BAR_HEIGHT * Game.deltaTime * BAR_SPEED_MULTIPLIER;
        if(currentBarHeight > BAR_HEIGHT) currentBarHeight = BAR_HEIGHT;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Display.width, currentBarHeight);
        g.fillRect(0, Display.height - currentBarHeight, Display.width, currentBarHeight);
    }

    public abstract void onCreate();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void onDestroy();

    public boolean hasControlled() { return hasControlled; }
}
