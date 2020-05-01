package com.harmony.game.state.chapters;

import com.harmony.game.entity.Player;
import com.harmony.game.entity.npc.NPC;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.ConsoleMessage;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.gui.GUI;
import com.harmony.game.object.Building;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Chapter2 extends Chapter {

    private final String playerQuestion = "Do you have a map I could have?";
    private ConsoleMessage message;

    private NPC mrsClark;

    public Chapter2() { super("/tile/places/kebir-town.tmx"); }

    @Override
    public void onCreate() {
        super.onCreate();

//        BackgroundAmbience.playBackgroundAudio(BackgroundAmbience.OUTSIDE_AMBIENCE);

        Camera.position = new Vector2f(2368,4967);
//        Camera.position = new Vector2f(4000, 1072);
        player.setCurrentAnimation(Player.ANIMATION_ATTACK_UP);
        player.setIdle(true);

        GUI.showMap = false;

        super.npcs.add(new NPC(new Vector2f(2795, 1726), "Miss. Tailor", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/tailor-c2.png", 64, 64), 128, 128,
                "I'm sorry no.~Ugh. Look at him. He thinks he can just decapitate fish. Not on my watch!!!", playerQuestion));

        super.npcs.add(new NPC(new Vector2f(3463, 1706), "Mr. Salem", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/fish-c2.png", 64, 64), 128, 128,
                "What? No...~Look at Miss. Tailor over there! Just because her name is Tailor doesn't mean she's a tailor!!!",
                playerQuestion));

        super.npcs.add(mrsClark = new NPC(new Vector2f(4926, 1300), "Mrs. Clark", tileManager.getObjectsMap(), player, console,
                new Sprite("/entity/npc/mrs-clark-c2.png", 64, 64), 128, 128,
                "Actually I accidentally bought a second. Here take it...~And remember you can press M at anytime" +
                        "to view the map completely.", playerQuestion));

        super.addGameObject(new Building(new Vector2f(2250, 4521), Building.Type.TAVERN));
        super.addGameObject(new Building(new Vector2f(2206, 873), Building.Type.HUT));
        super.addGameObject(new Building(new Vector2f(2275, 1550), Building.Type.TAILOR));
        super.addGameObject(new Building(new Vector2f(3324, 3863), Building.Type.VILLA));
        super.addGameObject(new Building(new Vector2f(4700, 725), Building.Type.HOUSE));
        super.addGameObject(new Building(new Vector2f(3246, 1630), Building.Type.FISH));
        super.addGameObject(new Building(new Vector2f(3227, 2733), Building.Type.INN));

        message = new ConsoleMessage(console, "I should see if anyone can give me a map.~" +
                "Then I can find-out what's happened to my world!", null);
        message.run();
    }

    @Override
    public void update() {
        super.update();

        if(mrsClark.hasTalked() && !GUI.showMap) GUI.showMap = true;

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
