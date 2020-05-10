package com.harmony.game.entity.enemy;

import com.harmony.game.animation.Animation;
import com.harmony.game.audio.AudioClip;
import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.item.Drops;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.state.chapters.Chapter;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Slime extends Enemy {

    public static final AudioClip slimeAttack = new AudioClip("/audio/entity/slime/slime_attack.wav");
    public static final AudioClip slimeHurt   = new AudioClip("/audio/entity/slime/slime_hurt.wav");

    static { slimeHurt.setGain(6f); }

    public Slime(Vector2f position, Chapter chapter, Player player, ObjectTileMap objectTileMap, int width, int height) {
        super(position, chapter, player, objectTileMap, width, height);

        this.rewardedCoins = 1;
        this.rewardedXp = 2;
    }

    @Override
    public void onCreate() {
        boxCollider = new BoxCollider(this, new Vector2f(0, 20), width, height - 20);
        detectionCollider = new BoxCollider(this, new Vector2f(-200, -200), width + 400, height + 400);

        sprite = new Sprite("/entity/enemy/slime.png", 18, 18);
        animation = new Animation(sprite);

        maxMoveSpeed = 2f;
        health = maxHealth = 4;
        damage = 1;

        currentAnimation = 0;
        isIdle = true;
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
        if(!isIdle) {
            g.drawImage(animation.animate(currentAnimation, 100), (int) position.getWorldPosition().x,
                    (int) position.getWorldPosition().y, width, height, null);
        } else {
            g.drawImage(animation.animate(currentAnimation, -1), (int) position.getWorldPosition().x,
                    (int) position.getWorldPosition().y, width, height, null);
        }

        super.draw(g);
    }

    @Override
    public void hit(int damage) {
        super.hit(damage);
        slimeHurt.play();
    }

    @Override
    public void onDeath() {
        player.awardXp(rewardedXp);
        player.awardCurrency((int) (Math.random() * 6));
    }

    @Override
    public void onDestroy() {
        if(isDead) Drops.drop(position, Drops.DROP_HEALTH_POINT);
    }
}
