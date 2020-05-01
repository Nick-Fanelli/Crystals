package com.harmony.game.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class ImageUtils implements Serializable {

    public static BufferedImage loadImage(String path) {
        System.out.println("Loading Image: " + path);
        try {
            return ImageIO.read(ImageUtils.class.getResourceAsStream(path));
        } catch (Exception e) {
            System.err.println("Crystals: Could not load resource: \"" + path + "\"");
            System.exit(-1);
        }
        return null;
    }

    public static BufferedImage getScaledImage(BufferedImage image, int width, int height) {
        BufferedImage r = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = r.getGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        return r;
    }
}
