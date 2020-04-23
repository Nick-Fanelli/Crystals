package com.harmony.game.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class ImageUtils implements Serializable {

    public static BufferedImage loadImage(String path) {
        System.out.println("Loading Image: " + path);
        try {
            return ImageIO.read(ImageUtils.class.getResourceAsStream(path));
        } catch (Exception e) {
            System.err.println("Could not load resource: \"" + path + "\"");
            System.exit(-1);
        }
        return null;
    }
}
