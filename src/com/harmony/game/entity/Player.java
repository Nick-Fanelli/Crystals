package com.harmony.game.entity;

import com.harmony.game.Game;
import com.harmony.game.audio.AudioClip;
import com.harmony.game.audio.SoundEffects;
import com.harmony.game.entity.enemy.Enemy;
import com.harmony.game.animation.Animation;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.object.GameObject;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.state.MenuState;
import com.harmony.game.state.chapters.Chapter;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.gui.GUI;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.Timer;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Entity {

//    public static final AudioClip maleAttack = new AudioClip("/audio/player/male/attack_male.wav");
    public AudioClip healthPoint;

    public static final int ANIMATION_RIGHT = 11;
    public static final int ANIMATION_LEFT  = 9;
    public static final int ANIMATION_DOWN  = 10;
    public static final int ANIMATION_UP    = 8;
    public static final int ANIMATION_ATTACK_RIGHT = 15;
    public static final int ANIMATION_ATTACK_LEFT  = 13;
    public static final int ANIMATION_ATTACK_DOWN  = 14;
    public static final int ANIMATION_ATTACK_UP    = 12;

    public boolean hide = false;

    public static int displayedHealth;

    private final Chapter chapter;
    private ArrayList<GameObject> gameObjects;

    private final BoxCollider attackCollider;
    private final Rectangle rectangle;

    public static int xp;
    public static int currency;
    public static int magicPoints;

    public Player(Chapter chapter, ObjectTileMap objectTileMap) {
        super(new Vector2f((Display.width / 2f) - 32, (Display.height / 2f) - 32), objectTileMap, 128, 128);

        this.chapter = chapter;

        this.rectangle = new Rectangle((int) position.x, (int) position.y, width, height);

        this.maxMoveSpeed = 15f; // Default 4
        this.acceleration = 15f; // Default 3

        health = maxHealth = 10;
        this.damage = 2;

        boxCollider = new BoxCollider(this, new Vector2f(40, width - 40), width - 80, 35);
        attackCollider = new BoxCollider(this, new Vector2f(-10, -10), width + 28, height + 28);

        if(MenuState.saveData != null){
            health = MenuState.saveData.playerSave.health;
            xp = MenuState.saveData.playerSave.xp;
            currency = MenuState.saveData.playerSave.currency;
            magicPoints = MenuState.saveData.playerSave.magicPoints;
        } else {
            xp = 0;
            currency = 0;
            magicPoints = 0;
        }

        healthPoint = new AudioClip("/audio/health_point_audio.wav");

        if(MenuState.saveData == null) sprite = new Sprite("/entity/player/male_light.png", 64, 64);
        else sprite = new Sprite(MenuState.saveData.playerSave.getPlayerImage(), 64, 64);

        animation = new Animation(sprite);

        isIdle = false;
    }

    public void setSpeed(int speed) { this.maxMoveSpeed = speed; }
    public void setGameObjects(ArrayList<GameObject> gameObjects) { this.gameObjects = gameObjects; }

    @Override
    public void onCreate() {
        currentAnimation = ANIMATION_RIGHT;
    }

    private void respawn() {
        Camera.reset();
        health = maxHealth;
        isDead = false;
    }

    @Override
    public void update() {
        if(chapter.isControlled()) return;
        if(isDead) respawn();

        // Set the displayed health
        displayedHealth = health;

        // Set player direction based on input
        up = Input.isKey(KeyEvent.VK_W);
        left = Input.isKey(KeyEvent.VK_A);
        down = Input.isKey(KeyEvent.VK_S);
        right = Input.isKey(KeyEvent.VK_D);
        attack = Input.isKey(KeyEvent.VK_SPACE);

        // Find Y Delta
        if (up) {
            dy -= acceleration;
            if (dy < -maxMoveSpeed) dy = -maxMoveSpeed;
        } else if (down) {
            dy += acceleration;
            if (dy > maxMoveSpeed) dy = maxMoveSpeed;
        } else {
            dy = 0;
        }

        // Find X Delta
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

        // START MOVEMENT ANIMATION
        if(!attack) {
            if (dx > 0) currentAnimation = ANIMATION_RIGHT;
            else if (dx < 0) currentAnimation = ANIMATION_LEFT;
            else if (dy > 0) currentAnimation = ANIMATION_DOWN;
            else if (dy < 0) currentAnimation = ANIMATION_UP;
            else isIdle = true;

            // Check for indirect direction change
            if (Input.isKeyUp(KeyEvent.VK_W) || Input.isKeyUp(KeyEvent.VK_A) || Input.isKeyUp(KeyEvent.VK_S) ||
                    Input.isKeyUp(KeyEvent.VK_D)) {
                if (Input.isKey(KeyEvent.VK_W)) currentAnimation = ANIMATION_UP;
                else if (Input.isKey(KeyEvent.VK_A)) currentAnimation = ANIMATION_LEFT;
                else if (Input.isKey(KeyEvent.VK_S)) currentAnimation = ANIMATION_DOWN;
                else if (Input.isKey(KeyEvent.VK_D)) currentAnimation = ANIMATION_RIGHT;
            }
        }

        // Attack animation
        if (Input.isKey(KeyEvent.VK_SPACE)) {
            if(currentAnimation == ANIMATION_RIGHT || currentAnimation == ANIMATION_ATTACK_RIGHT) {
                currentAnimation = ANIMATION_ATTACK_RIGHT;
            } else if(currentAnimation == ANIMATION_UP || currentAnimation == ANIMATION_ATTACK_UP) {
                currentAnimation = ANIMATION_ATTACK_UP;
            } else if(currentAnimation == ANIMATION_DOWN || currentAnimation == ANIMATION_ATTACK_DOWN) {
                currentAnimation = ANIMATION_ATTACK_DOWN;
            } else if(currentAnimation == ANIMATION_LEFT || currentAnimation == ANIMATION_ATTACK_LEFT) {
                currentAnimation = ANIMATION_ATTACK_LEFT;
            }

            attack = true;
            isIdle = false;

            dx = 0;
            dy = 0;
        } else {
            attack = false;
        }
        // END MOVEMENT ANIMATION

        checkAttack();

        // Adjust for Delta Time
        dx *= Game.deltaTime * speedMultiplier;
        dy *= Game.deltaTime * speedMultiplier;

        // Check game object collision
        if(collisionWithGameObject(dx, dy)) return;

        // Move Camera
        if (!boxCollider.collisionTilePlayer(objectTileMap, dx, 0)) {
            Camera.position.x += dx;
        }

        // Move Camera
        if (!boxCollider.collisionTilePlayer(objectTileMap, 0, dy)) {
            Camera.position.y += dy;
        }
    }

    private boolean collisionWithGameObject(float dx, float dy) {
        if(gameObjects == null) return false;

        for(GameObject object : gameObjects) {
            if(!object.isCollideable()) continue;
            if(object.isCollidingWithFuture(this, dx, dy)) return true;
        }

        return false;
    }

    private final Timer timer = new Timer();

    private void checkAttack() {
        if(chapter == null) return;
        if(chapter.isControlled()) return;
        for(Enemy enemy : chapter.getEnemies()) {
            if(!Camera.shouldHandleEntity(enemy)) continue;

            if(attack && attackCollider.getBoundsAsAbsRect().intersects(enemy.getBoxCollider().getBoundsAsRelativeRect())) {
                if(!timer.done()) continue;
                enemy.hit(damage);
                timer.delay(150);
                attack = false;
            }
        }
    }

    public void printPosition() {
        System.out.println("Player: " + position.add(Camera.position));
    }

    public void awardHealth(int amount) {
        System.out.println("-> Awarding Player " + amount + " Health");
        healthPoint.play();
        health = Math.min(health + amount, maxHealth);
    }

    public void awardCurrency(int amount) {
        System.out.println("-> Awarding Player " + amount + " Currency");
        SoundEffects.coinPickup.play();
        currency += amount;
    }

    public void awardXp(int amount) {
        System.out.println("-> Awarding Player " + amount + " XP");
        xp += amount;
    }

    @Override
    public void draw(Graphics2D g) {
        if(hide) return;
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
        healthPoint.close();
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

    public void setIdle(boolean isIdle) { this.isIdle = isIdle; }

    public Rectangle getRectangle() { return this.rectangle; }
}