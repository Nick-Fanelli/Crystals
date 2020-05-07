package com.harmony.game.state.chapters;

import com.harmony.game.animation.controller.C1Controller;
import com.harmony.game.animation.scene.Chapter1Scene1;
import com.harmony.game.audio.BackgroundAmbience;
import com.harmony.game.entity.npc.NPC;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.ConsoleMessage;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.gui.GUI;
import com.harmony.game.object.NextLevelInvisible;
import com.harmony.game.state.GameStateManager;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Chapter1 extends Chapter {

    private ConsoleMessage message;

    private NPC npcMrCrow = null;
    private NPC npcAmber = null;
    private NPC npcMrsCaren = null;
    private NPC matt = null;

    private NextLevelInvisible nextLevelInvisible;

    private boolean stage1Done = false;
    private boolean displayedCutScene = false;
    private boolean stage2Done = false;

    private C1Controller controller;

    public Chapter1() {
        super("/tile/places/kebir.tmx");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        GameStateManager.showCutScene(new Chapter1Scene1());

        BackgroundAmbience.playBackgroundAudio(BackgroundAmbience.OUTSIDE_AMBIENCE);

        handleNPC();

        GUI.showMap = false;

        Camera.position = new Vector2f(1000, 1000);
        Camera.setDefaultPosition();

        controller = new C1Controller(this);

        nextLevelInvisible = new NextLevelInvisible(new Vector2f(2364, 665), player, 320, 64);

        message = new ConsoleMessage(console, "Hello there... Welcome to the farm of Kebir.~" +
                "Use W, A, S, D to move around the farm.~" +
                "Great Job!!! Now try and find some locals to talk to.~" +
                "I should head towards the North trail and see if I can help...", null);
        message.runTo(3);
    }

    private void handleNPC() {
        super.npcs.add(npcMrCrow = new NPC(new Vector2f(1686, 3050), "Mr. Crow", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/mr-crow.png", 64, 64), 128, 128,
                "Would you look at my nice wheat. I think it's time to harvest that."));

        super.npcs.add(npcAmber = new NPC(new Vector2f(3520, 1974), "Amber", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/amber.png", 64, 64), 128, 128,
                "I sure do love these flowers. I wish they would stay year-round."));

        super.npcs.add(npcMrsCaren = new NPC(new Vector2f(2644, 1933), "Mrs. Caren", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/mrs-caren.png", 64, 64), 128, 128,
                "Hi Deary..."));

        super.npcs.add(matt = new NPC(new Vector2f(2141, 2572), "Matt", tileManager.getObjectsMap(),
                player, console, new Sprite("/entity/npc/matt.png", 64, 64), 128, 128,
                "I barley made it out alive. It came attackn' and didn't flinch after hit with an arrow. " +
                        "It took three whole arrows\nbefore it finally went skablat."));

        matt.hide = true;
        npcAmber.setCurrentAnimation(NPC.ANIMATION_DOWN);
        npcMrsCaren.setCurrentAnimation(NPC.ANIMATION_LEFT);
        npcMrCrow.setCurrentAnimation(NPC.ANIMATION_DOWN);
    }

    @Override
    public void update() {
        super.update();

        if(isControlled) controller.update();

        if(!stage1Done && npcAmber.hasTalked() && npcMrCrow.hasTalked()) stage1Done = true;

        if(stage1Done && !console.isShowConsole() && !displayedCutScene && !controller.hasControlled() && !isControlled) {
            displayedCutScene = true;
        }

        if(displayedCutScene) {
            displayedCutScene = false;
            isControlled = true;
            controller.onCreate();
        }

        if(stage2Done && npcMrsCaren.hasTalked() && npcMrsCaren.getMessage().getCurrentMessageID() >=
                npcMrsCaren.getMessage().getLines().length) {
            stage2Done = false;
            nextLevelInvisible.shouldDetect = true;
            message.runTo(4);
            console.setShowConsole(true);
        }

        nextLevelInvisible.update();

        message.update();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        if(isControlled) { controller.draw(g); }
        nextLevelInvisible.draw(g);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isControlled) controller.onDestroy();
    }

    public void updateCarenLines() {
        npcMrsCaren.changeLines("Oh, Hello Deary. I guess you're wondering about " +
                "that slime that attacked dear old Matty Boy. Well...~" +
                "Let me tell you something. There is an evil in this world that " +
                "is far beyond anything you could imagine.~" +
                "A long time ago there were five crystals, called The Crystals of Everything. " +
                "They were in fact crystals of \n everything.~" +
                "You see if you were holding the crystals they gave you gift.~" +
                "You see one gave speed, one gave strength, anther magic, " +
                "the next power of creation,\nand finally the power of destruction.~" +
                "These crystals were very valuable because people who had possession of the\n" +
                "became as powerful as gods.~" +
                "These crystals were placed by the old ones in the far reaches" +
                "of the world. Here no one could ever have\nthe power of all five crystals at once.~" +
                "However, rumor has it, someone by the name of The Dark Lord \n" +
                "gathered up all of the crystals and used them to spread evil throughout the world.~" +
                "He then banished the crystals so their magic could never be used again.~" +
                "However rumor has it that someone with pure intention can gather up all\n" +
                "of the crystals again and restore peace to the world.");
        stage2Done = true;
        matt.hide = false;
    }
}
