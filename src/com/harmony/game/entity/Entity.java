package com.harmony.game.entity;

import com.harmony.game.graphics.Animation;
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
    protected Animation animation;
    protected BoxCollider boxCollider;

    protected float maxMoveSpeed;
    protected float acceleration;

    protected int health;
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

    public void hit(int damage) {
        health -= damage;
        if(health <= 0) isDead = true;
        onDestroy();
    }

    public abstract void onCreate();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void onDestroy();

}
