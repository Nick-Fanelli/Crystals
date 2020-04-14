package com.harmony.game.entity.enemy;

import com.harmony.game.Game;
import com.harmony.game.audio.AudioClip;
import com.harmony.game.entity.Player;
import com.harmony.game.animation.Animation;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.item.Drops;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.state.levels.Level;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Slime extends Enemy {

    public static final AudioClip slimeEffect = new AudioClip("/audio/entity/slime/slime_hurt.wav");

    public static final int ANIMATION_RIGHT = 0;
    public static final int ANIMATION_LEFT  = 1;
    public static final int ANIMATION_UP    = 2;
    public static final int ANIMATION_DOWN  = 3;

    public Slime(Vector2f position, Level level, Player player, ObjectTileMap objectTileMap, int width, int height) {
        super(position, level, player, objectTileMap, width, height);
    }

    @Override
    public void onCreate() {
        boxCollider = new BoxCollider(this, new Vector2f(), width, height);
        detectionCollider = new BoxCollider(this, new Vector2f(-200, -200), width + 400, height + 400);

        sprite = new Sprite("/entity/enemy/slime.png", 18, 18);
        animation = new Animation(sprite);

        maxMoveSpeed = 2f;
        acceleration = 1.5f;
        health = maxHealth = 4;
        damage = 1;

        currentAnimation = 0;
        isIdle = true;
    }

    @Override
    public void update() {
        if(!Camera.shouldHandleEntity(this)) return;

        left = false;
        right = false;
        up = false;
        down = false;

        if(player.getBoxCollider().getBoundsAsAbsRect().intersects(detectionCollider.getBoundsAsRelativeRect())) {
            if(player.position.x - position.getWorldPosition().x > 0) {
                dx = Math.min(maxMoveSpeed, player.position.x - position.getWorldPosition().x);
                right = true;
                currentAnimation = ANIMATION_RIGHT;
            } else if(player.position.x - position.getWorldPosition().x < 0) {
                dx = Math.max(-maxMoveSpeed, player.position.x - position.getWorldPosition().x);
                left = true;
                currentAnimation = ANIMATION_LEFT;
            }

            if(player.position.y - position.getWorldPosition().y > 0) {
                dy = Math.min(maxMoveSpeed, player.position.y - position.getWorldPosition().y);
                down = true;
                currentAnimation = ANIMATION_DOWN;
            } else if(player.position.y - position.getWorldPosition().y < 0) {
                dy = Math.max(-maxMoveSpeed, player.position.y - position.getWorldPosition().y);
                up = true;
                currentAnimation = ANIMATION_UP;
            }
        } else {
            isIdle = true;
        }

        isIdle = (Math.ceil(dx) == 0 && Math.ceil(dy) == 0) || (Math.floor(dx) == 0 && Math.floor(dy) == 0);

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

        if(Game.debugMode) {
            g.setColor(Color.BLUE);
            g.drawRect((int) (position.getWorldPosition().x + boxCollider.getOffset().x), (int) (position.getWorldPosition().y + boxCollider.getOffset().y),
                    boxCollider.getWidth(), boxCollider.getHeight());

            g.setColor(Color.RED);
            g.drawRect((int) (position.getWorldPosition().x + detectionCollider.getOffset().x), (int) (position.getWorldPosition().y + detectionCollider.getOffset().y),
                    detectionCollider.getWidth(), detectionCollider.getHeight());
        }
    }

    @Override
    public void onDestroy() {
        if(isDead) Drops.drop(position, Drops.DROP_HEALTH_POINT);
    }
}
