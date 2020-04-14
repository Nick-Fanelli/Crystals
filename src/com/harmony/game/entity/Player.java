package com.harmony.game.entity;

import com.harmony.game.Game;
import com.harmony.game.audio.AudioClip;
import com.harmony.game.entity.enemy.Enemy;
import com.harmony.game.animation.Animation;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.state.MenuState;
import com.harmony.game.state.levels.Level;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.GUI;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {

    public static final AudioClip maleAttack = new AudioClip("/audio/player/male/attack_male.wav");
    public static final AudioClip healthPoint = new AudioClip("/audio/health_point_audio.wav");

    public static final int ANIMATION_RIGHT = 11;
    public static final int ANIMATION_LEFT  = 9;
    public static final int ANIMATION_DOWN  = 10;
    public static final int ANIMATION_UP    = 8;
    public static final int ANIMATION_ATTACK_RIGHT = 15;
    public static final int ANIMATION_ATTACK_LEFT  = 13;
    public static final int ANIMATION_ATTACK_DOWN  = 14;
    public static final int ANIMATION_ATTACK_UP    = 12;

    public static int staticHealth;

    private Level level;

    private final BoxCollider attackCollider;

    public Player(Level level, ObjectTileMap objectTileMap) {
        super(new Vector2f((Display.width / 2f) - 32, (Display.height / 2f) - 32), objectTileMap, 64, 64);

        this.level = level;

        this.maxMoveSpeed = 4f; // Default 4
        this.acceleration = 3f; // Default 3

        health = maxHealth = 10;
        this.damage = 2;

        boxCollider = new BoxCollider(this, new Vector2f(12, 40), 42, 20);
        attackCollider = new BoxCollider(this, new Vector2f(-30, -30), width + 60, height + 60);

        if(MenuState.saveData == null) sprite = new Sprite("/entity/player/male_light.png", 64, 64);
        else sprite = new Sprite(MenuState.saveData.playerSave.getPlayerImage(), 64, 64);

        animation = new Animation(sprite);

        isIdle = false;
    }

    public void setSpeed(int speed) { this.maxMoveSpeed = speed; }

    @Override
    public void onCreate() {
        currentAnimation = ANIMATION_RIGHT;
    }

    private void respawn() {
        Camera.position.reset();
    }

    @Override
    public void update() {
        if(isDead) respawn();

        staticHealth = health;

        up = Input.isKey(KeyEvent.VK_W);
        left = Input.isKey(KeyEvent.VK_A);
        down = Input.isKey(KeyEvent.VK_S);
        right = Input.isKey(KeyEvent.VK_D);
        attack = Input.isKey(KeyEvent.VK_SPACE);

        if (Input.isKeyDown(KeyEvent.VK_W)) currentAnimation = ANIMATION_UP;
        else if (Input.isKeyDown(KeyEvent.VK_A)) currentAnimation = ANIMATION_LEFT;
        else if (Input.isKeyDown(KeyEvent.VK_S)) currentAnimation = ANIMATION_DOWN;
        else if (Input.isKeyDown(KeyEvent.VK_D)) currentAnimation = ANIMATION_RIGHT;
        else if (Input.isKeyDown(KeyEvent.VK_SPACE)) {
            if(currentAnimation == ANIMATION_RIGHT || currentAnimation == ANIMATION_ATTACK_RIGHT) {
                currentAnimation = ANIMATION_ATTACK_RIGHT;
            } else if(currentAnimation == ANIMATION_UP || currentAnimation == ANIMATION_ATTACK_UP) {
                currentAnimation = ANIMATION_ATTACK_UP;
            } else if(currentAnimation == ANIMATION_DOWN || currentAnimation == ANIMATION_ATTACK_DOWN) {
                currentAnimation = ANIMATION_ATTACK_DOWN;
            } else if(currentAnimation == ANIMATION_LEFT || currentAnimation == ANIMATION_ATTACK_LEFT) {
                currentAnimation = ANIMATION_ATTACK_LEFT;
            }
        }
        else {
            isIdle = true;
        }

        if (up) {
            dy -= acceleration;
            if (dy < -maxMoveSpeed) dy = -maxMoveSpeed;
        } else if (down) {
            dy += acceleration;
            if (dy > maxMoveSpeed) dy = maxMoveSpeed;
        } else {
            dy = 0;
        }

        if (right) {
            dx += acceleration;
            if (dx > maxMoveSpeed) dx = maxMoveSpeed;
        } else if (left) {
            dx -= acceleration;
            if (dx < -maxMoveSpeed) dx = -maxMoveSpeed;
        } else {
            dx = 0;
        }

        if (up || down || right || left || attack) isIdle = false;
        else {
            dy = 0;
            dx = 0;
        }

        checkAttack();

        // Adjust for Delta Time
        dx *= Game.deltaTime * speedMultiplier;
        dy *= Game.deltaTime * speedMultiplier;

        if (!boxCollider.collisionTilePlayer(objectTileMap, dx, 0)) {
            Camera.position.x += dx;
        }

        if (!boxCollider.collisionTilePlayer(objectTileMap, 0, dy)) {
            Camera.position.y += dy;
        }
    }

    private void checkAttack() {
        if(level == null) return;
        for(Enemy enemy : level.getEnemies()) {
            if(!Camera.shouldHandleEntity(enemy)) return;

            if(attack && attackCollider.getBoundsAsAbsRect().intersects(enemy.getBoxCollider().getBoundsAsRelativeRect())) {
                enemy.hit(damage);
                attack = false;
            }
        }
    }

    public void awardHealth(int amount) {
        System.out.println("-> Awarding Player " + amount + " Health");
        healthPoint.play();
        health += amount;
        if(health > maxHealth) health = maxHealth;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(animation.animate(currentAnimation, getDelay(currentAnimation), getFrames(currentAnimation)), (int) position.x, (int) position.y,
                width, height, null);
        if(Game.debugMode) {
            g.setColor(Color.BLUE);
            g.drawRect((int) (position.x + boxCollider.getOffset().x), (int) (position.y + boxCollider.getOffset().y),
                    boxCollider.getWidth(), boxCollider.getHeight());

            if(attack) {
                g.setColor(Color.RED);
                g.drawRect((int) (position.x + attackCollider.getOffset().x), (int) (position.y + attackCollider.getOffset().y),
                        attackCollider.getWidth(), attackCollider.getHeight());
            }
        }
    }

    @Override
    public void hit(int damage) {
        GUI.hit = true;
        super.hit(damage);
    }

    @Override
    public void onDestroy() {

    }

    private int getDelay(int animation) {
        if(isIdle) return -1;
        if(animation == ANIMATION_RIGHT || animation == ANIMATION_LEFT || animation == ANIMATION_UP
                || animation == ANIMATION_DOWN) return 100;
        if(animation == ANIMATION_ATTACK_RIGHT || animation == ANIMATION_ATTACK_LEFT || animation == ANIMATION_ATTACK_UP
                || animation == ANIMATION_ATTACK_DOWN) return 10;
        return 0;
    }

    private int getFrames(int animation) {
        if(animation == ANIMATION_RIGHT || animation == ANIMATION_LEFT || animation == ANIMATION_UP
                || animation == ANIMATION_DOWN) return 9;
        if(animation == ANIMATION_ATTACK_RIGHT || animation == ANIMATION_ATTACK_LEFT || animation == ANIMATION_ATTACK_UP
                || animation == ANIMATION_ATTACK_DOWN) return 6;
        return 0;
    }
}
