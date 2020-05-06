package com.harmony.game.entity;

import com.harmony.game.animation.Animation;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public abstract class Entity {

    public Vector2f position;
    public float dx;
    public float dy;
    public int width;
    public int height;

    protected ObjectTileMap objectTileMap;

    protected boolean up      = false;
    protected boolean down    = false;
    protected boolean right   = false;
    protected boolean left    = false;
    protected boolean falling = false;
    protected boolean attack  = false;
    protected boolean isIdle  =  true;

    protected Sprite sprite;
    protected BoxCollider boxCollider;

    protected Animation animation;
    protected int currentAnimation;

    protected float maxMoveSpeed;
    protected float acceleration;

    public int health;
    public int maxHealth;
    protected int damage;

    protected float speedMultiplier = 35f;

    protected boolean isDead = false;

    public Entity(Vector2f position, ObjectTileMap objectTileMap, int width, int height) {
        this.position = position;
        this.objectTileMap = objectTileMap;
        this.width = width;
        this.height = height;

        this.onCreate();
    }

    public float getMaxMoveSpeed() { return maxMoveSpeed; }
    public float getAcceleration() { return acceleration; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public BoxCollider getBoxCollider() { return boxCollider; }
    public Vector2f getPosition() { return position; }

    public void setCurrentAnimation(int currentAnimation) { this.currentAnimation = currentAnimation; }

    public void hit(int damage) {
        if(isDead) return;
        health -= damage;
        System.out.println("-> " + getClass().getSimpleName() + " Hit - Damage: " + damage + ", Current Health: " + health);
        if(health <= 0) {
            isDead = true;
            onDestroy();
        }
    }

    public abstract void onCreate();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void onDestroy();

}
