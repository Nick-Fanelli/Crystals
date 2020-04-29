package com.harmony.game.gui;

import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.utils.ImageUtils;
import com.harmony.game.utils.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI {

    public static final Sprite blueCrystals = new Sprite("/item/crystals/blue-crystals.png", 32, 32);
    public static final Sprite greenCrystals = new Sprite("/item/crystals/green-crystals.png", 32, 32);
    public static final Sprite greyCrystals = new Sprite("/item/crystals/grey-crystals.png", 32, 32);
    public static final Sprite orangeCrystals = new Sprite("/item/crystals/orange-crystals.png", 32, 32);
    public static final Sprite yellowCrystals = new Sprite("/item/crystals/yellow-crystals.png", 32, 32);

    public static final Sprite collectables = new Sprite("/item/collectables.png", 24, 24);
    public static final Sprite health_points = new Sprite("/ui/hud/health_points.png", 11, 10);

    private static final BufferedImage health_holder = ImageUtils.loadImage("/ui/hud/health_holder.png");

    public static boolean showGui = false;
    public static boolean hasKey = false;

    public static boolean hit = false;
    private static final Timer hitTimer = new Timer();

    public static void update() {}

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

            g.drawImage(health_holder, 0, 0, 206, 48, null);

            float fullPoints = Player.staticHealth / 2f;
            float halfPoints = (fullPoints % 1);
            fullPoints -= halfPoints;
            halfPoints *= 2;

            float emptyPoints = 5 - (fullPoints + halfPoints);
            int i = 0;

            while(i < fullPoints) {
                g.drawImage(health_points.getSprite(0, 0), 22 + i * 30, 7, 24, 24, null);
                i++;
            }

            for(int j = 0; j < halfPoints; j++) {
                g.drawImage(health_points.getSprite(1, 0), 22 + i * 30, 7, 24, 24, null);
                i++;
            }

            for(int j = 0; j < emptyPoints; j++) {
                g.drawImage(health_points.getSprite(2, 0), 22 + i * 30, 7, 24, 22, null);
                i++;
            }

        }
    }

    public static void cleanUp() {
        health_holder.flush();
    }
}
