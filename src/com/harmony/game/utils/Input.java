package com.harmony.game.utils;

import javax.swing.*;
import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static final int NUM_KEYS = 256;
    private static boolean[] keys     = new boolean[NUM_KEYS];
    private static boolean[] keysLast = new boolean[NUM_KEYS];

    public static final int NUM_BUTTONS = 5;
    private static boolean[] buttons     = new boolean[NUM_BUTTONS];
    private static boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private static Vector2f mouse = new Vector2f();
    private static int scroll;

    public Input(JFrame frame) {
        scroll = 0;

        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        frame.addMouseWheelListener(this);
    }

    public void update() {
        for(int i = 0; i < NUM_KEYS; i++)       keysLast[i] =    keys[i];
        for(int i = 0; i < NUM_BUTTONS; i++) buttonsLast[i] = buttons[i];
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

    @Override public void keyPressed(KeyEvent e) { if(e.getKeyCode() <= keys.length) keys[e.getKeyCode()] = true; }
    @Override public void keyReleased(KeyEvent e) { if(e.getKeyCode() <= keys.length) keys[e.getKeyCode()] = false; }

    @Override public void mousePressed(MouseEvent e) { if(e.getButton() <= buttons.length) buttons[e.getButton()] = true; }
    @Override public void mouseReleased(MouseEvent e) { if(e.getButton() <= buttons.length) buttons[e.getButton()] = false; }

    @Override public void mouseDragged(MouseEvent e) { mouse.x = e.getX(); mouse.y = e.getY(); }
    @Override public void mouseMoved(MouseEvent e) { mouse.x = e.getX(); mouse.y = e.getY(); }
    @Override public void mouseWheelMoved(MouseWheelEvent e) { scroll = e.getWheelRotation(); }

    @Deprecated @Override public void keyTyped(KeyEvent keyEvent) {}
    @Deprecated @Override public void mouseClicked(MouseEvent e) {}
    @Deprecated @Override public void mouseEntered(MouseEvent e) {}
    @Deprecated @Override public void mouseExited(MouseEvent e) {}
}
