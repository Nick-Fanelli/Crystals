package com.harmony.game.physics.collision;

import java.awt.image.BufferedImage;

public class Mesh {

    private final boolean[] triggers;
    private final int imageWidth;
    private final int imageHeight;

    private final int w;
    private final int h;

    public Mesh(BufferedImage image, int w, int h) {
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();

        this.w = w;
        this.h = h;

        triggers = new boolean[image.getWidth() * image.getHeight()];

        int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

        for (int i = 0; i < pixels.length; i++) {
            triggers[i] = pixels[i] != 0;
        }
    }

    public Mesh(BufferedImage image) { this(image, image.getWidth(), image.getHeight()); }


    public int getImageWidth() { return imageWidth; }
    public int getImageHeight() { return imageHeight; }

    public boolean isCollidable(int x, int y) {
        if(x >= w) return false;
        if(x >= h) return false;
        return triggers[x + y * imageWidth];
    }
}
