package com.harmony.game;

public class Launcher {

    public static void main(String[] args) {
        System.out.println("Starting a new game instance at: " + new Game().toString().substring(21));
    }
    
}
