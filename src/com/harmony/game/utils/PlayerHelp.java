package com.harmony.game.utils;

import com.harmony.game.animation.Animation;
import com.harmony.game.graphics.Sprite;

import java.awt.*;

public class PlayerHelp {

    public static final int E_ANIMATION = 0;
    public static final int T_ANIMATION = 1;

    private static Sprite buttonSprites = new Sprite("/ui/button_guides.png", 21, 21);
    private static Animation buttonAnimation = new Animation(buttonSprites);

    private static final int buttonSize = 32;

    public static void showLetter(Graphics2D g, int x, int y, int animation) {
        g.drawImage(buttonAnimation.animate(animation, 1000), x, y, buttonSize, buttonSize, null);
    }
}
