package com.harmony.game.state.chapters;

import com.harmony.game.entity.enemy.Slime;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.ConsoleMessage;
import com.harmony.game.object.Building;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Chapter3 extends Chapter {

    public Chapter3() {
        super("/tile/places/forest-c3.tmx");
    }

    private ConsoleMessage message;

    @Override
    public void onCreate() {
        Camera.position = new Vector2f(968, 487);

        super.onCreate();

        super.addGameObject(new Building(new Vector2f(3380, 2574), Building.Type.HUT));
        super.addGameObject(new Building(new Vector2f(3309, 1560), Building.Type.VILLA));

        super.enemies.add(new Slime(new Vector2f(2392, 887), this, player, tileManager.getObjectsMap(),
                64, 64));

        message = new ConsoleMessage(console, "I think there's one of those slimes up ahead...~Use the space-bar while facing " +
                "the slime to attack.~Good Luck...", null);

        message.run();
    }

    @Override
    public void update() {
        super.update();

//        player.printPosition();

        message.update();
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
