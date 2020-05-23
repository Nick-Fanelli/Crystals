package com.harmony.game.item;

import com.harmony.game.entity.Player;
import com.harmony.game.gui.GUI;
import com.harmony.game.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item {

    public static final int EMPTY = 0;
    public static final int HEALTH_POINT = 1;
    public static final int CURRENCY_10  = 2;
    public static final int XP_10        = 3;

    public static void givePlayer(Player player, int item) {
        switch (item) {
            case EMPTY: break;

            case HEALTH_POINT:
                player.awardHealth(1);
                break;

            case CURRENCY_10:
                player.awardCurrency(10);
                break;

            case XP_10:
                player.awardXp(10);
                break;
        }
    }

    public static void displayItem(Graphics2D g, int item, int xPos, int yPos, int width) {
        BufferedImage image = null;

        switch (item) {
            case EMPTY: break;

            case HEALTH_POINT:
                image = ImageUtils.getScaledImage(GUI.health_points.getSprite(0, 0), 32, 32);
                break;

            case CURRENCY_10:
                image = ImageUtils.getScaledImage(GUI.collectables.getSprite(0, 0), 32, 32);
                break;
        }

        if(image == null) return;

        g.drawImage(image, xPos + (width / 2) - image.getWidth() / 2, yPos, null);
    }
}
