package com.harmony.game.graphics;

import com.harmony.game.Game;
import com.harmony.game.state.GameStateManager;
import com.harmony.game.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Display {

    public static final BufferedImage logo = ImageUtils.loadImage("/ui/logo.png");

    public static int width;
    public static int height;

    public static float absWidth;
    public static float absHeight;

    private static Dimension d;

    private static JFrame frame;
    private static BufferedImage image;
    private static Canvas canvas;
    private static BufferStrategy bs;
    private static Graphics2D g;

    public Display(String title, int width, int height) {
        Display.width = width;
        Display.height = height;

        Display.absWidth = width;
        Display.absHeight = height;

        Display.d = new Dimension(width, height);

        setProperties();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));

        if(System.getProperty("os.name").equals("Mac OS X")) {
            frame = new JFrame();
        } else {
            frame = new JFrame(title);
        }

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = (Graphics2D) bs.getDrawGraphics();

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(System.getProperty("os.name").equals("Mac OS X")) {
            SwingUtilities.invokeLater(() -> {
                frame.getRootPane().putClientProperty("apple.awt.fullWindowContent", true);
                frame.getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
            });
        }

        frame.setVisible(true);

        handleResize();
        handleQuit();


        //showSplashScreen();
    }

    public static void showSplashScreen() {
        g.drawImage(logo, 0, 0, (int) absWidth, (int) absHeight, null);
        bs.show();

        Game.sleep(2000);
    }

    private void setProperties() {
        System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
    }

    private void handleResize() {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(frame.getWidth() <= 0 || frame.getHeight() <= 0) return;
                absWidth = frame.getWidth();
                absHeight = frame.getHeight();
                canvas.createBufferStrategy(2);
                bs = canvas.getBufferStrategy();
                g = (Graphics2D) bs.getDrawGraphics();
            }
        });
    }

    private void handleQuit() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameStateManager.requestCloseConfirmation();
            }
        });
    }

    public static void update() {
        try {
            g.drawImage(image, 0, 0, (int) absWidth, (int) absHeight, null);
            bs.show();
        } catch(Exception e) {}
    }

    public static void setCursor(int type) { frame.setCursor(new Cursor(type)); }

    public JFrame getFrame() { return frame; }
    public BufferedImage getImage() { return image; }
    public Canvas getCanvas() { return canvas; }
    public BufferStrategy getBufferStrategy() { return bs; }
    public Graphics getFrameGraphics() { return g; }
}
