package com.harmony.game.entity.npc;

import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Animation;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Console;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class GuideNPC extends NPC {

    public static final int ANIMATION_RIGHT = 0;
    public static final int ANIMATION_LEFT  = 1;
    public static final int ANIMATION_DOWN  = 2;
    public static final int ANIMATION_UP    = 3;

    public GuideNPC(Vector2f position, Player player, Console console, ObjectTileMap objectTileMap) {
        super(position, objectTileMap, player, console, 64, 64,
                "Hello... My name is Zoe. I am your guide.~" +
                        "I'm so glad you came because I've heard rumors that there are " +
                "five crystals \nthroughout the world.~" +
                        "These crystals have the power to give you strength, magic, speed, and much more.~" +
                        "If you can find all of the crystals, you can become the strongest person alive.~" +
                "Then you can finally restore peace to the world.~" +
                "Start by learning to attack, use the space bar to fight your enemies.~" +
                "Good Luck!!!");
    }

    @Override
    public void onCreate() {
        sprite = new Sprite("/entity/npc/guide.png", 48, 48);
        animation = new Animation(sprite);
        boxCollider = new BoxCollider(this, new Vector2f(), width, height);
        talkCollider = new BoxCollider(this, new Vector2f(-50, -50), width + 100, height + 100);

        currentAnimation = ANIMATION_RIGHT;
    }

    @Override
    public void update() {
        if(!Camera.shouldHandleEntity(this)) return;

        super.update();
    }

    @Override
    public void draw(Graphics2D g) {
        if(!Camera.shouldHandleEntity(this)) return;

        g.drawImage(animation.animate(currentAnimation, -1), (int) position.getWorldPosition().x,
                (int) position.getWorldPosition().y, width, height, null);

        super.draw(g);
    }

    @Override
    public void onDestroy() {

    }
}
