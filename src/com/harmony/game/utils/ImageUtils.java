package com.harmony.game.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageUtils {

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImageUtils.class.getResourceAsStream(path));
        } catch (Exception e) {
            System.err.println("Could not load resource: \"" + path + "\"");
            System.exit(-1);
        }
        return null;
    }

}
