package com.harmony.game.state.levels;

import com.harmony.game.audio.BackgroundMusic;

import java.awt.*;

public class Level1 extends Level {

    public Level1() { super("/tile/level_1/dungeon_map.tmx"); }

    @Override
    public void onCreate() {
        super.onCreate();

        BackgroundMusic.playBackgroundAudio(BackgroundMusic.CAVE_BACKGROUND_AUDIO);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
