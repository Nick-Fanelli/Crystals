package com.harmony.game.state.levels;

import com.harmony.game.audio.BackgroundMusic;
import com.harmony.game.entity.enemy.Slime;
import com.harmony.game.entity.npc.GuideNPC;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Level1 extends Level {

    public Level1() { super("/tile/level_1/dungeon_map.tmx"); }

    private GuideNPC guide;

    @Override
    public void onCreate() {
        super.onCreate();

        BackgroundMusic.playBackgroundAudio(BackgroundMusic.CAVE_BACKGROUND_AUDIO);

        guide = new GuideNPC(new Vector2f(800, 400), player, console, (ObjectTileMap) tileManager.getObjectsMap());
        guide.setCurrentAnimation(GuideNPC.ANIMATION_LEFT);

        enemies.add(new Slime(new Vector2f(617, 1143), this, player, (ObjectTileMap) tileManager.getObjectsMap(), 64, 64));
    }

    @Override
    public void update() {
        super.update();
        guide.update();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        guide.draw(g);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
