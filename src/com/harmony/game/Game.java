package com.harmony.game;

import com.harmony.game.audio.BackgroundAmbience;
import com.harmony.game.graphics.Display;
import com.harmony.game.state.GameStateManager;
import com.harmony.game.state.MenuState;
import com.harmony.game.gui.GUI;
import com.harmony.game.utils.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game implements Runnable {

    public static final int targetFps = 60;
    public static final double UPDATE_CAP = 1.0 / targetFps;

    public static final double deltaTime = 0.029;

    public static boolean debugMode = false;

    public static Color backgroundColor = new Color(34, 30, 39);

    private final Thread gameThread;
    private Display display;
    private Graphics2D g;

    private GameStateManager gsm;
    private Input input;

    private static boolean isRunning;

    public Game() {
        initialize();

        gameThread = new Thread(this, "_GameThread_");
        gameThread.start();
    }

    private void initialize() {
        display = new Display("Crystals", 1280, 720);

        g = (Graphics2D) display.getImage().getGraphics();

        input = new Input(display.getFrame(), display.getCanvas());

        gsm = new GameStateManager(g);

        GameStateManager.setCurrentState(GameStateManager.CHPATER_2);
    }

    @Override
    public void run() {
        isRunning = true;

        boolean draw;

        double firstTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double deltaTime = 0;

        while(isRunning) {

            draw = false;

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            deltaTime += passedTime;

            while(deltaTime >= UPDATE_CAP) {
                deltaTime -= UPDATE_CAP;
                draw = true;
                update();
            }

            if(draw) {
                draw();
                Display.update();
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("*** Cleaning Up ***");
        cleanUp();
    }

    public static boolean requestFocus = true;

    private synchronized void update() {
        gsm.update();

        if(requestFocus) display.getFrame().requestFocus();

        if(Input.isKeyDown(KeyEvent.VK_F3)) debugMode = !debugMode;

        input.update();
    }

    private synchronized void draw() {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Display.width, Display.height);
        gsm.draw();
    }

    private void cleanUp() {
        if(MenuState.saveData != null) MenuState.saveData.save();

        BackgroundAmbience.cleanUp();
        GUI.cleanUp();

        System.out.println("*** Closing Game ***");             System.exit(1);
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void setIsRunning(boolean isRunning) { Game.isRunning = isRunning; }
    public static boolean isRunning() { return isRunning; }
}
