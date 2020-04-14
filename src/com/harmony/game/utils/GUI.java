package com.harmony.game.utils;

import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI {

    public static final Sprite blueCrystals = new Sprite("/item/crystals/blue-crystals.png", 32, 32);
    public static final Sprite greenCrystals = new Sprite("/item/crystals/green-crystals.png", 32, 32);
    public static final Sprite greyCrystals = new Sprite("/item/crystals/grey-crystals.png", 32, 32);
    public static final Sprite orangeCrystals = new Sprite("/item/crystals/orange-crystals.png", 32, 32);
    public static final Sprite yellowCrystals = new Sprite("/item/crystals/yellow-crystals.png", 32, 32);

    private static final BufferedImage mainGUI = ImageUtils.loadImage("/ui/hud/main_gui.png");

    private static final Sprite increments = new Sprite("/ui/hud/increments.png", 8, 12);
    public static final Sprite collectables = new Sprite("/item/collectables.png", 24, 24);

    public static boolean showGui = false;
    public static boolean hasKey = false;

    public static boolean hit = false;
    private static Timer hitTimer = new Timer();

    public static void update() {

    }

    public static void draw(Graphics2D g) {
        if(hit) {
            hitTimer.delay(100);
            hit = false;
        }

        if(hitTimer.isActive()) {
            hitTimer.done();
            g.setColor(new Color(255, 0, 0, 65));
            g.fillRect(0, 0, Display.width, Display.height);
        }

        if(showGui) {
            g.drawImage(mainGUI, 10, 10, (int) (mainGUI.getWidth() * 1.5), (int) (mainGUI.getHeight() * 1.5), null);

            // Health Points
            for(int i = 0; i < Player.staticHealth; i++) {
                g.drawImage(increments.getSprite(0, 0), 136 + (i * 14) - (i / 2), 16, 12, 21,null);
            }

            if (!hasKey) g.drawImage(collectables.getSprite(2, 0), Display.width - 70, 6, 64, 64, null);
            else g.drawImage(collectables.getSprite(3, 0), Display.width - 70, 6, 64, 64, null);
        }
    }

    public Sprite getCollectables() { return collectables; }

}
