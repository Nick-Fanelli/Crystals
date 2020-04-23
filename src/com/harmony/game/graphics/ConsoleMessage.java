package com.harmony.game.graphics;

public class ConsoleMessage {

    private final Console console;
    private final String[] lines;
    private final String sender;

    private boolean words = false;
    private boolean shouldLimit = false;

    private int limit;
    private int i;

    public ConsoleMessage(Console console, String message, String sender) { this(console, message, sender, "~"); }

    public ConsoleMessage(Console console, String message, String sender, String regex) {
        this.console = console;
        this.sender = sender;

        i = 0;
        lines = message.split(regex);
    }

    public void run() {
        i = 0;
        limit = 0;
        shouldLimit = false;
        words = true;
        console.setShowConsole(true);
    }

    public void runTo(int i) {
        limit = i;
        shouldLimit = true;
        words = true;
        console.setShowConsole(true);
    }

    public void update() {
        if(!words) return;
        if(shouldLimit && i >= limit) { words = false; return; }

        if(console.isWaiting()) return;

        if(i >= lines.length) {
            console.setShowConsole(false);
            words = false;
            return;
        }

        console.sendMessage(lines[i], sender);
        i++;
    }

    public int getCurrentMessageID() { return i; }
    public String[] getLines() { return lines; }
    public boolean isActive() { return words; }
}
