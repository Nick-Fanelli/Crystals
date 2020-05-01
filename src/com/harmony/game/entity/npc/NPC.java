package com.harmony.game.entity.npc;

import com.harmony.game.Game;
import com.harmony.game.animation.Animation;
import com.harmony.game.audio.AudioClip;
import com.harmony.game.entity.Entity;
import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Console;
import com.harmony.game.graphics.ConsoleMessage;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.PlayerHelp;
import com.harmony.game.utils.Timer;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public class NPC extends Entity {

    public static final int ANIMATION_RIGHT = 11;
    public static final int ANIMATION_LEFT  = 9;
    public static final int ANIMATION_DOWN  = 10;
    public static final int ANIMATION_UP    = 8;

    protected final String name;
    protected final String preMessage;

    protected BoxCollider talkCollider;

    protected Player player;
    protected Sprite sprite;

    protected Console console;
    protected ConsoleMessage message;
    protected boolean hasTalked = false;

    public boolean hide = false;

    public NPC(Vector2f position, String name, ObjectTileMap objectTileMap, Player player, Console console, Sprite sprite,
               int width, int height, String lines, String preMessage) {
        super(position, objectTileMap, width, height);

        this.name = name;
        this.player = player;
        this.console = console;
        this.sprite = sprite;
        this.preMessage = preMessage;

        message = new ConsoleMessage(console, lines, name);

        this.onCreate();
    }

    public NPC(Vector2f position, String name, ObjectTileMap objectTileMap, Player player, Console console, Sprite sprite,
               int width, int height, String lines) {
        this(position, name, objectTileMap, player, console, sprite, width, height, lines, null);
    }

    @Override
    public void onCreate() {
        maxMoveSpeed = 3f;

        animation = new Animation(sprite);
        boxCollider = new BoxCollider(this, new Vector2f(), width, height);
        talkCollider = new BoxCollider(this, new Vector2f(-50, -50), width + 100, height + 100);

        currentAnimation = ANIMATION_RIGHT;
    }

    @Override
    public void update() {
        if(hide) return;
        if(!Camera.shouldHandleEntity(this)) return;

        if(talkCollider.collisionPlayer(player) && Input.isKeyDown(KeyEvent.VK_T) && !message.isActive()) {
            hasTalked = true;
            if(preMessage != null) console.sendMessage(preMessage, null);
            message.run();
        }

        message.update();

        if (!boxCollider.collisionTile(objectTileMap, dx, 0)) {
            if(dx > 0) {
                position.x += dx;
            } else {
                position.x += dy;
            }
        }

        if (!boxCollider.collisionTile(objectTileMap, 0, dy)) {
            position.y += Math.min(maxMoveSpeed, dy * Game.deltaTime * speedMultiplier);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if(hide) return;
        if(!Camera.shouldHandleEntity(this)) return;

        g.drawImage(animation.animate(currentAnimation, isIdle ? -1 : 100, 9), (int) position.getWorldPosition().x,
                (int) position.getWorldPosition().y, width, height, null);

        if(talkCollider.collisionPlayer(player) && !message.isActive()) {
            PlayerHelp.showLetter(g, (int) player.position.x - 10, (int) player.position.y - 10, PlayerHelp.T_ANIMATION);
        }
    }

    @Override
    public void onDestroy() {

    }

    public boolean hasTalked() { return hasTalked; }
    public void setHasTalked(boolean b) { this.hasTalked = b; }

    public void changeLines(String lines) {
        hasTalked = false;
        message = new ConsoleMessage(console, lines, name);
    }

    public ConsoleMessage getMessage() { return message; }

}
