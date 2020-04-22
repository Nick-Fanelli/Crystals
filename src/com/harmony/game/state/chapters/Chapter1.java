package com.harmony.game.state.chapters;

import com.harmony.game.Game;
import com.harmony.game.entity.npc.NPC;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.ConsoleMessage;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Chapter1 extends Chapter {

    private ConsoleMessage message;

    private NPC npcMrCrow;

    public Chapter1() {
        super("/tile/places/kebir.tmx");
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        GameStateManager.showCutScene(new Chapter1Scene1());

        //BackgroundAmbience.playBackgroundAudio(BackgroundAmbience.OUTSIDE_AMBIENCE);
        Game.backgroundColor = new Color(0x8FB0BD);

        super.npcs.add(npcMrCrow = new NPC(new Vector2f(1686, 3050), "Mr. Crow", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/mr-crow.png", 64, 64), 128, 128,
                "Would you look at my nice wheat. I think it's time to harvest that."));

        Camera.position = new Vector2f(1000, 1000);

        message = new ConsoleMessage(console, "Hello there... Welcome to the farm of Kebir.~" +
                "Use W, A, S, D to move around the farm.~" +
                "Great Job!!! Now try and find some locals to talk to.", null);
        message.run();
    }

    @Override
    public void update() {
        super.update();

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
