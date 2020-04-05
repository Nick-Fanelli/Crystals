package com.harmony.game;

import com.harmony.game.state.GameStateManager;
import com.harmony.game.utils.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {

    public static double targetFps = 60.0;
    public static final double UPDATE_CAP = 1.0 / targetFps;

    public static double deltaTime = 0;

    public static int width;
    public static int height;

    private static int scaledWidth;
    private static int scaledHeight;

    public static boolean debugMode = false;

    private Thread gameThread;
    private JFrame frame;
    private BufferedImage image;
    private BufferedImage windowImage;
    private Graphics2D g;
    private Graphics2D windowGraphics;

    private GameStateManager gsm;
    private Input input;

    private static boolean isRunning = false;

    public GamePanel(Dimension startingDimension, JFrame frame) {
        this.frame = frame;

        GamePanel.width  = startingDimension.width  ;
        GamePanel.height = startingDimension.height ;
        GamePanel.scaledHeight = startingDimension.height ;
        GamePanel.scaledWidth  = startingDimension.width  ;

        super.requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if(gameThread != null) return;
        gameThread = new Thread(this, "_GameThread_");
        gameThread.start();
    }

    private void initializeWindowGraphics() {
        windowImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        windowGraphics = (Graphics2D) windowImage.getGraphics();
        windowGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void handleResize() {
        super.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(GamePanel.super.getWidth() <= 0 || GamePanel.super.getHeight() <= 0) return;
                scaledWidth  = GamePanel.super.getWidth() ;
                scaledHeight = GamePanel.super.getHeight();
                initializeWindowGraphics();
            }
        });
    }

    private void handleClose() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
            }
        });
    }

    private void initialize() {
        handleResize();
        handleClose();

        image = new BufferedImage(GamePanel.width, GamePanel.height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        initializeWindowGraphics();

        gsm = new GameStateManager();
        input = new Input(frame);

        GameStateManager.setCurrentState(GameStateManager.PRACTICE_STATE);
    }

    public static void stop() { isRunning = false; }

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
                GamePanel.deltaTime = deltaTime;
                deltaTime -= UPDATE_CAP;
                draw = true;
                update();
            }

            if(draw) {
                draw();
                drawToScreen();
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        cleanUp();
    }

    private void cleanUp() {
        frame.dispose();
        System.exit(1);
    }

    private synchronized void update() {
        gsm.update();

        if(Input.isKeyDown(KeyEvent.VK_F3)) debugMode = !debugMode;

        input.update();
    }

    private synchronized void draw() {
        g.setColor(new Color(47, 123, 255));
        g.fillRect(0, 0, width, height);
        gsm.draw(g);
    }

    private synchronized void drawToScreen() {
        Graphics g2 = super.getGraphics();
        windowGraphics.drawImage(image, 0, 0, windowImage.getWidth(), windowImage.getHeight(), null);
        g2.drawImage(windowImage, 0, 0, windowImage.getWidth(), windowImage.getHeight(), null);
        g2.dispose();
    }

}
