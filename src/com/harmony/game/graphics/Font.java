package com.harmony.game.graphics;

import java.awt.*;

public class Font {

    public static final Font STANDARD_FONT = new Font();

    public void centerText(Graphics2D g, String text, int fontSize) {
        java.awt.Font font = new java.awt.Font("Comic Sans MS", java.awt.Font.PLAIN, fontSize);
        drawText(g, text, (Display.width / 2) - (g.getFontMetrics(font).stringWidth(text) / 2),
                (Display.height / 2), fontSize);
    }

    public void centerTextHorizontal(Graphics2D g, String text, int y, int fontSize) {
        java.awt.Font font = new java.awt.Font("Comic Sans MS", java.awt.Font.PLAIN, fontSize);
        drawText(g, text, (Display.width / 2) - (g.getFontMetrics(font).stringWidth(text) / 2), y, fontSize);
    }

    public void drawText(Graphics2D g, String text, int x, int y, int fontSize) {
        this.drawText(g, text, x, y, new java.awt.Font("Comic Sans MS", java.awt.Font.PLAIN, fontSize), Color.WHITE);
    }

    public void drawText(Graphics2D g, String text, int x, int y, int fontSize, Color color) {
        this.drawText(g, text, x, y, new java.awt.Font("Comic Sans MS", java.awt.Font.PLAIN, fontSize), color);
    }

    public void drawText(Graphics2D g, String text, int x, int y, java.awt.Font font, Color color) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(text, x, y);
    }

}