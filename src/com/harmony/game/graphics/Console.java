package com.harmony.game.graphics;

import com.harmony.game.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Console {

    public Font font = Font.TRANSPARENT_FONT;

    private boolean showConsole = false;
    private boolean hasText = false;
    private boolean delay = false;

    private String[] lines = new String[0];
    private String[] outputLines = new String[0];
    private int currentLine;
    private int currentChar;

    private float stopTime;
    private int delayMillis = 1000;

    private BufferedImage consoleImage = ImageUtils.loadImage("/ui/console.png");

    public void update() {
        if(!showConsole) return;
        if(!hasText) return;

        if(delay) {
            stopTime = System.currentTimeMillis() + delayMillis;
            delay = false;
        }

        if(System.currentTimeMillis() < stopTime) return;

        if(currentChar < lines[currentLine].length()) {
            outputLines[currentLine] += lines[currentLine].charAt(currentChar);
            currentChar++;
        } else {
            if(currentLine < lines.length - 1) {
                currentChar = 0;
                currentLine++;
            } else {
                hasText = false;
                return;
            }
        }

        delay = true;
    }

    public void draw(Graphics2D g) {
        if(!showConsole) return;
        g.setColor(new Color(28, 28, 28));
        g.drawImage(consoleImage, 80, 540, null);

        // Draw Text
        for(int l = 0; l < outputLines.length; l++) {
            for(int i = 0; i < outputLines[l].length(); i++) {
                font.drawText(g, outputLines[l].substring(0, i), 90, 550 + l * 20, 20);
            }
        }
//        if(!currentMessage.isBlank()) font.drawText(g, currentMessage, 90, 550, 20);
    }

    public void sendMessage(String text) {
        lines = text.split("\n");
        for(int i = 0; i < lines.length; i++) lines[i] += " ";
        outputLines = new String[lines.length];
        Arrays.fill(outputLines, "");
        delay = true;
        currentLine = 0;
        currentChar = 0;
        hasText = true;
    }

    public void setShowConsole(boolean showConsole) { this.showConsole = showConsole; }
    public boolean getShowConsole() { return showConsole; }

}
