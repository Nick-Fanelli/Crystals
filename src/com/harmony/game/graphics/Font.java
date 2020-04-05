package com.harmony.game.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Font {

    public static final Font STANDARD_FONT = new Font(new Sprite("/font/font.png", 10, 10));
    public static final Font TRANSPARENT_FONT = new Font(new Sprite("/font/font-only.png", 10, 10));

    private Sprite sprite;
    private int letterWidth;
    private int letterHeight;

    public Font(Sprite sprite) {
        this.sprite = sprite;
        this.letterWidth = sprite.getTileWidth();
        this.letterHeight = sprite.getTileHeight();
    }

    public BufferedImage[] getText(String text) {
        char[] chars = text.toCharArray();
        BufferedImage[] sprites = new BufferedImage[chars.length];

        for(int i = 0; i < chars.length; i++) {
            sprites[i] = sprite.getSprite(chars[i]);
        }

        return sprites;
    }

    public void drawText(Graphics2D g, String text, int x, int y, int fontSize) {
        BufferedImage[] images = getText(text);

        for(int i = 0; i < images.length; i++) {
            g.drawImage(images[i], x + (i * (int) (fontSize / 1.5f)), y, fontSize, fontSize, null);
        }
    }

    public void drawText(Graphics2D g, String text, int x, int y) {
        drawText(g, text, x, y, letterWidth);
    }

    public Sprite getSprite() { return sprite; }
    public int getLetterWidth() { return letterWidth; }
    public int getLetterHeight() { return letterHeight; }
}
