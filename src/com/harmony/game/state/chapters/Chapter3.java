package com.harmony.game.state.chapters;

import com.harmony.game.audio.BackgroundAmbience;
import com.harmony.game.entity.enemy.Slime;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.ConsoleMessage;
import com.harmony.game.item.Drops;
import com.harmony.game.item.Item;
import com.harmony.game.object.Building;
import com.harmony.game.object.Chest;
import com.harmony.game.object.NextLevelInvisible;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Chapter3 extends Chapter {

    private NextLevelInvisible nextLevelInvisible = null;

    private ConsoleMessage message;

    public Chapter3() { super("/tile/places/forest-c3.tmx"); }

    @Override
    public void onCreate() {
        Camera.position = new Vector2f(968f, 487f);
        Camera.setDefaultPosition();
        super.onCreate();

        BackgroundAmbience.playBackgroundAudio(BackgroundAmbience.OUTSIDE_AMBIENCE);

        message = new ConsoleMessage(console, "I think there's one of those slimes up ahead...~Use the space-bar while facing " +
                "the slime to attack.~Good Luck...", null);
        message.run();

        super.addGameObject(new Building(new Vector2f(3380f, 2574f), Building.Type.HUT));
        super.addGameObject(new Building(new Vector2f(3309f, 1560f), Building.Type.VILLA));
        super.enemies.add(new Slime(new Vector2f(2392f, 887f), this, player, tileManager.getObjectsMap(),
                64, 64));
        super.enemies.add(new Slime(new Vector2f(3717f, 844f), this, player, tileManager.getObjectsMap(),
                64, 64));
        super.enemies.add(new Slime(new Vector2f(6212f, 885f), this, player, tileManager.getObjectsMap(),
                64, 64));
        super.enemies.add(new Slime(new Vector2f(5605f, 6565f), this, player, tileManager.getObjectsMap(),
                64, 64));

        Drops.drop(new Vector2f(5170f, 805f), Drops.DROP_HEALTH_POINT);
        Drops.drop(new Vector2f(3076f, 830f), Drops.DROP_HEALTH_POINT);

        for (Chest chest : chests) { chest.setItem(Item.CURRENCY_10); }

        nextLevelInvisible = new NextLevelInvisible(new Vector2f(7819f, 7244f), player, 96, 140);
        nextLevelInvisible.shouldDetect = true;
    }

    @Override
    public void update() {
        super.update();

        message.update();
        nextLevelInvisible.update();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        nextLevelInvisible.draw(g);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}