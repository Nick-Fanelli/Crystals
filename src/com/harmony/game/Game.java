package com.harmony.game;

import com.harmony.game.graphics.Display;
import com.harmony.game.state.GameStateManager;
import com.harmony.game.utils.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game implements Runnable {

    public static double targetFps = 60.0;
    public static final double UPDATE_CAP = 1.0 / targetFps;

    public static double deltaTime = 0;

    public static boolean debugMode = false;

    private Thread gameThread;
    private Display display;
    private Graphics2D g;

    private GameStateManager gsm;
    private Input input;

    private static boolean isRunning = false;

    public Game() {
        gameThread = new Thread(this, "_GameThread_");
        gameThread.start();
    }

    private void initialize() {
        display = new Display("Game", 1280, 720);
        g = (Graphics2D) display.getImage().getGraphics();

        input = new Input(display.getFrame());

        gsm = new GameStateManager();

        GameStateManager.setCurrentState(GameStateManager.PRACTICE_STATE);
    }

    @Override
    public void run() {
        initialize();
        isRunning = true;

        boolean draw;

        double firstTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double deltaTime = 0;

        while(isRunning) {

            draw = false;

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            deltaTime += passedTime;

            while(deltaTime >= UPDATE_CAP) {
                Game.deltaTime = deltaTime;
                deltaTime -= UPDATE_CAP;
                draw = true;
                update();
            }

            if(draw) {
                draw();
                display.update();
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private synchronized void update() {
        gsm.update();

        display.getFrame().requestFocus();

        if(Input.isKeyDown(KeyEvent.VK_F3)) debugMode = !debugMode;

        input.update();
    }

    private synchronized void draw() {
        g.setColor(new Color(47, 123, 255));
        g.fillRect(0, 0, Display.width, Display.height);
        gsm.draw(g);
    }

}
