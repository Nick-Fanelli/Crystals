package com.harmony.game.graphics;

import com.harmony.game.animation.scene.Scene;
import com.harmony.game.audio.AudioClip;
import com.harmony.game.utils.ImageUtils;
import com.harmony.game.utils.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Console {

    public static final int CONSOLE_Y_DEFAULT = 498;
    public static final int CONSOLE_Y_MODIFIED = CONSOLE_Y_DEFAULT - Scene.BAR_HEIGHT;

    public Font font = Font.TRANSPARENT_FONT;
    public AudioClip typeClip = new AudioClip("/audio/console_letter_sound.wav");
    public static final String SENDER_DEFAULT = "You";

    private boolean showConsole = false;
    private boolean hasText = false;
    private boolean delay = false;
    private boolean waitForEnter = false;
    private boolean waiting = false;

    private String[] lines = new String[0];
    private String[] outputLines = new String[0];
    private int currentLine;
    private int currentChar;

    private float stopTime;
    private int delayMillis = 1000;

    private int consoleYPosition = CONSOLE_Y_DEFAULT;

    private String sender = SENDER_DEFAULT;

    private BufferedImage consoleImage = ImageUtils.loadImage("/ui/console.png");

    public void update() {
        if(!showConsole) return;

        if(waitForEnter && Input.isKeyDown(KeyEvent.VK_ENTER)) {
            waiting = false;
        }

        if(!hasText) { return; }

        if(delay) {
            stopTime = System.currentTimeMillis() + delayMillis;
            delay = false;
        }

        if(System.currentTimeMillis() < stopTime) return;

        if(currentChar < lines[currentLine].length()) {
            if(!typeClip.isActive()) typeClip.play();
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
        g.drawImage(consoleImage, 80, consoleYPosition, null);

        // Draw Sender Name
        font.drawText(g, sender, Display.width / 2 - (int) (sender.length() * 10 / 1.5), consoleYPosition + 12, 20);

        // Draw Text
        for(int l = 0; l < outputLines.length; l++) {
            for(int i = 0; i < outputLines[l].length(); i++) {
                font.drawText(g, outputLines[l].substring(0, i), 90, consoleYPosition + 52 + l * 20, 20);
            }
        }
    }

    public void pushMessage(String text, String sender) {
        if(sender != null) this.sender = sender; else this.sender = SENDER_DEFAULT;
        System.out.println("-> Pushing Message: " + text);
        lines = text.split("\n");
        for(int i = 0; i < lines.length; i++) lines[i] += " ";
        outputLines = new String[lines.length];
        Arrays.fill(outputLines, "");
        delay = true;
        currentLine = 0;
        currentChar = 0;
        hasText = true;
        waitForEnter = false;
        waiting = false;
    }

    public void sendMessage(String text, String sender) {
        if(sender != null) this.sender = sender; else this.sender = SENDER_DEFAULT;
        System.out.println("-> Sending Message: " + text);
        text += "\nPress Enter To Continue...";
        lines = text.split("\n");
        for(int i = 0; i < lines.length; i++) lines[i] += " ";
        outputLines = new String[lines.length];
        Arrays.fill(outputLines, "");
        delay = true;
        currentLine = 0;
        currentChar = 0;
        hasText = true;
        waitForEnter = true;
        waiting = true;
    }

    public void setShowConsole(boolean showConsole) {
        this.showConsole = showConsole;
        if(showConsole) System.out.println("-> Opening Console");
        else System.out.println("-> Closing Console");
    }

    public boolean isWaiting() { return waiting; }
    public boolean isShowConsole() { return showConsole; }

    public void cleanUp() {
        typeClip.close();
    }

    public void setConsoleYPosition(int position) { this.consoleYPosition = position; }
    public int getConsoleYPosition() { return consoleYPosition; }
}
