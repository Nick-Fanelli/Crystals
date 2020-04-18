package com.harmony.game.graphics;

public class ConsoleMessage {

    private final Console console;
    private final String[] lines;
    private boolean words = false;
    private int i;

    public ConsoleMessage(Console console, String message) { this(console, message, "~"); }

    public ConsoleMessage(Console console, String message, String regex) {
        this.console = console;
        lines = message.split(regex);
    }

    public void run() { words = true; }

    public void update() {
        if(!words) return;

        if(console.isWaiting()) return;

        if(i >= lines.length) {
            console.setShowConsole(false);
            words = false;
            return;
        }

        if(i <= 0) console.setShowConsole(true);

        console.sendMessage(lines[i]);
        i++;
    }

    public int getCurrentMessageID() { return i; }
    public String[] getLines() { return lines; }
    public boolean isActive() { return words; }
}
