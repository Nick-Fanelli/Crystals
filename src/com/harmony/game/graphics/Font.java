package com.harmony.game.graphics;

import java.awt.*;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class Font {

    public static final Font STANDARD_FONT = new Font("Comic Sans MS");

    private final String fontName;
    public Font(String fontName) { this.fontName = fontName; }

    public void centerTextRect(Graphics2D g, String text, int fontSize, Rectangle rectangle) {
        java.awt.Font font = new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize);

        drawText(g, text, (rectangle.x + (rectangle.width / 2) - (g.getFontMetrics(font).stringWidth(text) / 2)),
                rectangle.y + fontSize + rectangle.height / 2 - (int) (fontSize * 0.75), fontSize);
    }

    public void centerText(Graphics2D g, String text, int fontSize) {
        java.awt.Font font = new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize);
        drawText(g, text, (Display.width / 2) - (g.getFontMetrics(font).stringWidth(text) / 2),
                (Display.height / 2), fontSize);
    }

    public void centerTextHorizontal(Graphics2D g, String text, int y, int fontSize) {
        java.awt.Font font = new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize);
        drawText(g, text, (Display.width / 2) - (g.getFontMetrics(font).stringWidth(text) / 2), y, fontSize);
    }

    public void drawText(Graphics2D g, String text, int x, int y, int fontSize) {
        this.drawText(g, text, x, y, new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize), Color.WHITE);
    }

    public void drawText(Graphics2D g, String text, int x, int y, int fontSize, Color color) {
        this.drawText(g, text, x, y, new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize), color);
    }

    public void drawText(Graphics2D g, String text, int x, int y, java.awt.Font font, Color color) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public String getFontName() { return fontName; }
    public java.awt.Font getFont(int fontSize) { return new java.awt.Font(fontName, java.awt.Font.PLAIN, fontSize); }
}