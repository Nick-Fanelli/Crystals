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
    private static final BufferedImage mini_map = ImageUtils.loadImage("/ui/hud/mini-map.png");
    private static final BufferedImage map = ImageUtils.loadImage("/ui/map.png");
    private static final BufferedImage menu = ImageUtils.loadImage("/ui/menu.png");

    public static boolean showGui = false;
    public static boolean hasKey = false;
    public static boolean showMap = true;
    public static boolean displayFullMap = false;
    public static boolean displayMenu = false;

    public static boolean hit = false;
    private static final Timer hitTimer = new Timer();

    public static void update() { }

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

            if(displayMenu) {
                g.drawImage(menu, 40, 40, null);
            }

            // Player Health
            if(!displayMenu) g.drawImage(health_holder, 0, 0, 206, 48, null);

            float fullPoints = Player.displayedHealth / 2f;
            float halfPoints = (fullPoints % 1);
            fullPoints -= halfPoints;
            halfPoints *= 2;

            float emptyPoints = 5 - (fullPoints + halfPoints);
            int i = 0;

            while(i < fullPoints) {
                g.drawImage(health_points.getSprite(0, 0), (displayMenu ? 877 : 22) + (i * 30), displayMenu ? 155 : 7, 24, 24, null);
                i++;
            }

            for(int j = 0; j < halfPoints; j++) {
                g.drawImage(health_points.getSprite(1, 0), (displayMenu ? 877 : 22) + i * 30, displayMenu ? 155 : 7, 24, 24, null);
                i++;
            }

            for(int j = 0; j < emptyPoints; j++) {
                g.drawImage(health_points.getSprite(2, 0), (displayMenu ? 877 : 22) + i * 30, displayMenu ? 155 : 7, 24, 22, null);
                i++;
            }

            // Map
            if(showMap && !displayMenu) {
                g.drawImage(mini_map, Display.width - 110, Display.height - 110, 100, 100, null);

                if(displayFullMap) {
                    g.setColor(new Color(0x99C9C9C9, true));
                    g.fillRect(0, 0, Display.width, Display.height);
                    g.drawImage(map, Display.width / 2 - 320, Display.height / 2 - 320, 640, 640, null);
                }
            }


        }
    }

    public static void cleanUp() {
        health_holder.flush();
    }
}
