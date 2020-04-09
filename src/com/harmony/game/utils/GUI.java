package com.harmony.game.utils;

import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Sprite;

import java.awt.*;

public class GUI {


    public static final Sprite collectables = new Sprite("/item/collectables.png", 24, 24);

    public static boolean showGui = false;
    public static boolean hasKey = false;

    public static void update() {

    }

    public static void draw(Graphics2D g) {
        if(showGui) {
            if (!hasKey) g.drawImage(collectables.getSprite(2, 0), Display.width - 70, 6, 64, 64, null);
            else g.drawImage(collectables.getSprite(3, 0), Display.width - 70, 6, 64, 64, null);
        }
    }

    public Sprite getCollectables() { return collectables; }

}
