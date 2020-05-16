package com.harmony.game.state.chapters;

import com.harmony.game.audio.BackgroundAmbience;
import com.harmony.game.entity.Player;
import com.harmony.game.entity.npc.NPC;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.ConsoleMessage;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.gui.GUI;
import com.harmony.game.object.Building;
import com.harmony.game.object.NextLevelInvisible;
import com.harmony.game.utils.Vector2f;
import java.awt.*;

public class Chapter2 extends Chapter {

    private ConsoleMessage message;
    private ConsoleMessage finalMessage;

    private NextLevelInvisible nextLevelInvisible;

    private NPC mrsClark;

    public Chapter2() { super("/tile/places/kebir-town.tmx"); }

    @Override
    public void onCreate() {
        super.onCreate();

        BackgroundAmbience.playBackgroundAudio(BackgroundAmbience.OUTSIDE_AMBIENCE);

        Camera.position = new Vector2f(2368f, 4967f);
        Camera.setDefaultPosition();

        player.setCurrentAnimation(Player.ANIMATION_ATTACK_UP);
        player.setIdle(true);

        GUI.showMap = false;

        String playerQuestion = "Do you have a map I could have?";

        super.npcs.add(new NPC(new Vector2f(2795f, 1726f), "Miss. Tailor", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/tailor-c2.png", 64, 64), 128, 128,
                "I'm sorry no.~Ugh. Look at him. He thinks he can just decapitate fish. Not on my watch!!!", playerQuestion));
        super.npcs.add(new NPC(new Vector2f(3463f, 1706f), "Mr. Salem", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/fish-c2.png", 64, 64), 128, 128,
                "What? No...~Look at Miss. Tailor over there! Just because her name is Tailor doesn't mean she's a tailor!!!",
                playerQuestion));
        super.npcs.add(mrsClark = new NPC(new Vector2f(4926f, 1300f), "Mrs. Clark", tileManager.getObjectsMap(), player, console,
                new Sprite("/entity/npc/mrs-clark-c2.png", 64, 64), 128, 128,
                "Actually I accidentally bought a second. Here take it...~And remember you can press M at anytime " +
                        "to view the map completely.", playerQuestion));

        nextLevelInvisible = new NextLevelInvisible(new Vector2f(5400f, 2100f), player, 20, 350);

        super.addGameObject(new Building(new Vector2f(2250f, 4521f), Building.Type.TAVERN));
        super.addGameObject(new Building(new Vector2f(2206f, 873f), Building.Type.HUT));
        super.addGameObject(new Building(new Vector2f(2275f, 1550f), Building.Type.TAILOR));
        super.addGameObject(new Building(new Vector2f(3324f, 3863f), Building.Type.VILLA));
        super.addGameObject(new Building(new Vector2f(4700f, 725f), Building.Type.HOUSE));
        super.addGameObject(new Building(new Vector2f(3246f, 1630f), Building.Type.FISH));
        super.addGameObject(new Building(new Vector2f(3227f, 2733f), Building.Type.INN));

        finalMessage = new ConsoleMessage(console, "According to the map, I should head down the east trail.~" +
                "The red line is my path, (Hint: I am in the top left to start).", null);

        message = new ConsoleMessage(console, "I should see if anyone can give me a map.~" +
                "Then I can find-out what's happened to my world!", null);
        message.run();
    }

    @Override
    public void update() {
        super.update();

        if (mrsClark.hasTalked() && !console.isShowConsole() && !GUI.showMap) {
            GUI.showMap = true;
            finalMessage.run();
            nextLevelInvisible.shouldDetect = true;
        }

        message.update();
        finalMessage.update();
        nextLevelInvisible.update();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        nextLevelInvisible.draw(g);
    }

}