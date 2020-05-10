package com.harmony.game.state.chapters;

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

    private NextLevelInvisible nextLevelInvisible;

    public Chapter3() {
        super("/tile/places/forest-c3.tmx");
    }

    private ConsoleMessage message;

    @Override
    public void onCreate() {
//        Camera.position = new Vector2f(5602, 1598);
        Camera.position = new Vector2f(968, 487);
        Camera.setDefaultPosition();

        super.onCreate();

//        BackgroundAmbience.playBackgroundAudio(BackgroundAmbience.OUTSIDE_AMBIENCE);

        message = new ConsoleMessage(console, "I think there's one of those slimes up ahead...~Use the space-bar while facing " +
                "the slime to attack.~Good Luck...", null);

        message.run();

        super.addGameObject(new Building(new Vector2f(3380, 2574), Building.Type.HUT));
        super.addGameObject(new Building(new Vector2f(3309, 1560), Building.Type.VILLA));

        super.enemies.add(new Slime(new Vector2f(2392, 887), this, player, tileManager.getObjectsMap(),
                64, 64));

        super.enemies.add(new Slime(new Vector2f(3717, 844), this, player, tileManager.getObjectsMap(),
                64, 64));

        super.enemies.add(new Slime(new Vector2f(6212, 885), this, player, tileManager.getObjectsMap(),
                64, 64));

        super.enemies.add(new Slime(new Vector2f(5605, 6565), this, player, tileManager.getObjectsMap(),
                64, 64));

        Drops.drop(new Vector2f(5170, 805), Drops.DROP_HEALTH_POINT);
        Drops.drop(new Vector2f(3076, 830), Drops.DROP_HEALTH_POINT);

        for(Chest chest : chests) {
            chest.setItem(Item.CURRENCY_10);
        }

        nextLevelInvisible = new NextLevelInvisible(new Vector2f(4952, 7498), player, 400, 32);
        nextLevelInvisible.shouldDetect = true;
    }

    @Override
    public void update() {
        super.update();

//        player.printPosition();

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
