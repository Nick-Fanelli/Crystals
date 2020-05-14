package com.harmony.game.entity.enemy;

import com.harmony.game.animation.Animation;
import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.state.chapters.Chapter;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Spider extends Enemy {

    public Spider(Vector2f position, Chapter chapter, Player player, ObjectTileMap objectTileMap) {
        super(position, chapter, player, objectTileMap, 64, 42);

        this.rewardedXp = 5;
        this.rewardedCoins = 3;

        super.ANIMATION_DOWN = super.ANIMATION_UP;
    }

    @Override
    public void onCreate() {
        sprite = new Sprite("/entity/enemy/spider.png", 35, 23);
        animation = new Animation(sprite);

        boxCollider = new BoxCollider(this, new Vector2f(0, 20), width, height - 20);
        detectionCollider = new BoxCollider(this, new Vector2f(-200, -200), width + 400, height + 400);

        maxMoveSpeed = 2.5f;
        health = maxHealth = 5;
        damage = 1;
    }

    @Override
    public void update() {
        if(!Camera.shouldHandleEntity(this)) return;

        super.playerPathfinderAI();
        super.update();
    }

    @Override
    public void draw(Graphics2D g) {
        if(!Camera.shouldHandleEntity(this)) return;

        g.drawImage(animation.animate(currentAnimation, isIdle ? -1 : 75), (int) position.getWorldPosition().x,
                (int) position.getWorldPosition().y, width, height, null);

        super.draw(g);
    }

    @Override
    public void onDestroy() {

    }
}
