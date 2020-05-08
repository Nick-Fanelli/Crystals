package com.harmony.game.utils;

import com.harmony.game.graphics.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static final int NUM_KEYS = 256;
    private static final boolean[] keys     = new boolean[NUM_KEYS];
    private static final boolean[] keysLast = new boolean[NUM_KEYS];

    public static final int NUM_BUTTONS = 5;
    private static final boolean[] buttons     = new boolean[NUM_BUTTONS];
    private static final boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private static boolean movementKeyPressed = false;

    private static final Vector2f mouse = new Vector2f();
    private static int scroll;

    public Input(JFrame frame, Canvas canvas) {
        scroll = 0;

        frame.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);

        canvas.setFocusable(true);
        canvas.requestFocus();
    }

    public void update() {
        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);
        System.arraycopy(buttons, 0, buttonsLast, 0, NUM_BUTTONS);

        movementKeyPressed = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_A] || keys[KeyEvent.VK_S] || keys[KeyEvent.VK_D];

//        System.out.println(mouse);
    }

    public static boolean hoverRectangle(Rectangle rectangle) {
        return mouse.x >= rectangle.x && mouse.x <= rectangle.x + rectangle.width &&
               mouse.y >= rectangle.y && mouse.y <= rectangle.y + rectangle.height;
    }


    // Keys
    public static boolean isKey(int keycode) { return keys[keycode]; }
    public static boolean isKeyUp(int keycode) { return !keys[keycode] && keysLast[keycode]; }
    public static boolean isKeyDown(int keycode) { return keys[keycode] && !keysLast[keycode]; }

    // Buttons
    public static boolean isButton(int button) { return buttons[button]; }
    public static boolean isButtonUp(int button) { return !buttons[button] && buttonsLast[button]; }
    public static boolean isButtonDown(int button) { return buttons[button] && !buttonsLast[button]; }

    // Mouse
    public static Vector2f getMouse() { return mouse; }
    public static int getScroll() { return scroll; }

    @Override public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() <= keys.length) keys[e.getKeyCode()] = true;
    }

    @Override public void keyReleased(KeyEvent e) { if(e.getKeyCode() <= keys.length) keys[e.getKeyCode()] = false; }

    @Override public void mousePressed(MouseEvent e) { if(e.getButton() <= buttons.length) buttons[e.getButton()] = true; }
    @Override public void mouseReleased(MouseEvent e) { if(e.getButton() <= buttons.length) buttons[e.getButton()] = false; }

    @Override public void mouseDragged(MouseEvent e) {
        mouse.x = e.getX() / (Display.absWidth / Display.width);
        mouse.y = e.getY() / (Display.absHeight / Display.height);
    }

    @Override public void mouseMoved(MouseEvent e) {
        mouse.x = e.getX() / (Display.absWidth / Display.width);
        mouse.y = e.getY() / (Display.absHeight / Display.height);
    }

    @Override public void mouseWheelMoved(MouseWheelEvent e) { scroll = e.getWheelRotation(); }

    @Deprecated @Override public void keyTyped(KeyEvent keyEvent) {}
    @Deprecated @Override public void mouseClicked(MouseEvent e) {}
    @Deprecated @Override public void mouseEntered(MouseEvent e) {}
    @Deprecated @Override public void mouseExited(MouseEvent e) {}

    public static boolean movementKeyPressed() { return movementKeyPressed; }
}
