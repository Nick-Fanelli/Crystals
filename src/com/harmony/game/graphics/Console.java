package com.harmony.game.graphics;

import com.harmony.game.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Console {

    public Font font = Font.TRANSPARENT_FONT;
    private boolean showConsole = false;

    private String currentMessage = "";
    private String targetMessage = "";

    private boolean delay;
    private double stopTime;
    private int delayMillis = 10;

    private BufferedImage consoleImage = ImageUtils.loadImage("/ui/console.png");

    public void update() {
        if(!showConsole) return;
        if(targetMessage.isBlank()) return;
        if(targetMessage.equals(currentMessage)) return;

        if(delay) {
            stopTime = System.currentTimeMillis() + delayMillis;
            delay = false;
        }

        if(System.currentTimeMillis() < stopTime) return;

        currentMessage += targetMessage.substring(currentMessage.length(), currentMessage.length() + 1);

        delay = true;
    }

    public void draw(Graphics2D g) {
        if(!showConsole) return;
        g.setColor(new Color(28, 28, 28));
        g.drawImage(consoleImage, 80, 540, null);

        // Draw Text
        if(!currentMessage.isBlank()) font.drawText(g, currentMessage, 90, 550, 20);
    }

    public void sendMessage(String text) {
        if(!showConsole) {
            System.err.println("Must show console first before: \"" + text + "\"");
            return;
        }

        currentMessage = "";
        this.targetMessage = text;
    }

    public void setShowConsole(boolean showConsole) { this.showConsole = showConsole; }
    public boolean getShowConsole() { return showConsole; }

}
