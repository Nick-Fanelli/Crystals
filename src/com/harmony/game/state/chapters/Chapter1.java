package com.harmony.game.state.chapters;

import com.harmony.game.Game;
import com.harmony.game.animation.scene.chapter1.Chapter1Scene1;
import com.harmony.game.state.GameStateManager;

import java.awt.*;

public class Chapter1 extends Chapter {

    public Chapter1() {
        super("/tile/places/kebir.tmx");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GameStateManager.showCutScene(new Chapter1Scene1());
        Game.backgroundColor = new Color(0x8FB0BD);
    }

}
