package com.harmony.game.state.chapters;

import com.harmony.game.entity.Player;
import com.harmony.game.entity.npc.NPC;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.object.Building;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Chapter2 extends Chapter {

    public Chapter2() { super("/tile/places/kebir-town.tmx"); }

    @Override
    public void onCreate() {
        super.onCreate();

//        BackgroundAmbience.playBackgroundAudio(BackgroundAmbience.OUTSIDE_AMBIENCE);

//        Camera.position = new Vector2f(2368,4967);
        Camera.position = new Vector2f(4000, 1072);
        player.setCurrentAnimation(Player.ANIMATION_ATTACK_UP);
        player.setIdle(true);

        super.npcs.add(new NPC(new Vector2f(2795, 1726), "Miss. Tailor", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/tailor-c2.png", 64, 64), 128, 128,
                "Ugh. Look at him. He thinks he can just decapitate fish. Not on my watch!!!"));

        super.npcs.add(new NPC(new Vector2f(3463, 1706), "Mr. Salem", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/fish-c2.png", 64, 64), 128, 128,
                "Look at Miss. Tailor over there! Just because her name is Tailor doesn't mean she's a tailor!!!"));

        super.addGameObject(new Building(new Vector2f(2250, 4521), Building.Type.TAVERN));
        super.addGameObject(new Building(new Vector2f(2206, 873), Building.Type.HUT));
        super.addGameObject(new Building(new Vector2f(2275, 1550), Building.Type.TAILOR));
        super.addGameObject(new Building(new Vector2f(3324, 3863), Building.Type.VILLA));
        super.addGameObject(new Building(new Vector2f(4700, 725), Building.Type.HOUSE));
        super.addGameObject(new Building(new Vector2f(3246, 1630), Building.Type.FISH));
        super.addGameObject(new Building(new Vector2f(3227, 2733), Building.Type.INN));
    }

    @Override
    public void update() {
        super.update();

//        System.out.println(player.getPosition().add(Camera.position));
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
