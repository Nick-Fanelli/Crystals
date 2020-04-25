package com.harmony.game.animation.controller;

import com.harmony.game.Game;
import com.harmony.game.animation.Animation;
import com.harmony.game.entity.npc.NPC;
import com.harmony.game.graphics.*;
import com.harmony.game.state.chapters.Chapter;
import com.harmony.game.state.chapters.Chapter1;
import com.harmony.game.gui.GUI;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class C1Controller extends Controller {

    private Sprite matt;
    private Animation mattAnimation;
    private ConsoleMessage mattMessage;

    private Sprite papi;
    private ConsoleMessage papiMessage;

    private Vector2f originalPosition;

    private int stage = 0;

    public C1Controller(Chapter chapter) { super(chapter); }

    @Override
    public void onCreate() {
        GUI.showGui = false;
        chapter.getPlayer().hide = true;
        originalPosition = Camera.position;
        Camera.position = new Vector2f(786, 2200);

        chapter.getConsole().setConsoleYPosition(Console.CONSOLE_Y_MODIFIED);

        matt = new Sprite("/entity/npc/matt.png", 64, 64);
        mattAnimation = new Animation(matt);

        papi = new Sprite("/entity/npc/papi.png", 64, 64);

        mattMessage = new ConsoleMessage(chapter.getConsole(), "*Gasps for air* Oh my...~*Gasps for air* " +
                "gosh...~They're getting stronger...~The slime creatures... It took three arrows to kill them.", "Matt");

        papiMessage = new ConsoleMessage(chapter.getConsole(), "Who's getting stronger?", "Papi");
    }

    @Override
    public void update() {
        if(Camera.position.x < 1500) Camera.position.x += Game.deltaTime * 28 * 6;
        else if(stage == 0) {
            stage++;
            mattMessage.runTo(3);
        }

        if(stage == 1) {
            papiMessage.run();
            stage++;
        }

        if(stage == 2 && !papiMessage.isActive()) {
            mattMessage.runTo(5);
            stage++;
        }

        if(stage == 3 && !mattMessage.isActive()) {
            chapter.getConsole().sendMessage("I should talk to Mrs. Caren, she ought to know what's happening here...", null);
            chapter.getConsole().setShowConsole(true);
            stage++;
        }

        if(stage == 4 && !chapter.getConsole().isWaiting()) {
            chapter.getConsole().setShowConsole(false);
            this.onDestroy();
        }

        mattMessage.update();
        papiMessage.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(mattAnimation.animate(NPC.ANIMATION_RIGHT, stage <= 1 ? 100 : -1, 9),
                (int) (Display.width / 2f - 64),
                (int) (Display.height / 2f - 64), 128, 128, null);

        g.drawImage(papi.getSprite(0, 9), (int) Vector2f.toRelativeX(1500 + Display.width - 512),
                (int) (Display.height / 2f - 64), 128, 128, null);

        super.makeCinematic(g);
    }

    @Override
    public void onDestroy() {
        hasControlled = true;
        chapter.getConsole().setConsoleYPosition(Console.CONSOLE_Y_DEFAULT);
        Camera.position = originalPosition;
        ((Chapter1) chapter).updateCarenLines();
        chapter.getPlayer().hide = false;
        GUI.showGui = true;
        chapter.setControlled(false);
    }
}
