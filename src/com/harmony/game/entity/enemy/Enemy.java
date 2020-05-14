package com.harmony.game.entity.enemy;

import com.harmony.game.Game;
import com.harmony.game.entity.Entity;
import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Camera;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.state.chapters.Chapter;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public abstract class Enemy extends Entity  {

    protected final Player player;

    protected BoxCollider detectionCollider;

    private final Chapter chapter;

    protected int ANIMATION_RIGHT = 0;
    protected int ANIMATION_LEFT  = 1;
    protected int ANIMATION_UP    = 2;
    protected int ANIMATION_DOWN  = 3;

    protected int rewardedCoins;
    protected int rewardedXp;

    protected boolean isInvincible = false;

    private int totalMoveX;
    private int totalMoveY;
    private MoveDirection xMoveDirection;
    private MoveDirection yMoveDirection;

    protected int moveBackDistance = 100;
    protected int moveBackSpeed = 15;

    public enum MoveDirection { POSITIVE, NEGATIVE };

    public Enemy(Vector2f position, Chapter chapter, Player player, ObjectTileMap objectTileMap, int width, int height) {
        super(position, objectTileMap, width, height);

        this.chapter = chapter;
        this.player = player;
    }

    private void playHitEffect() {
        if(getClass() == Slime.class) Slime.slimeAttack.play();
    }

    private void playHurtEffect() {
        if(getClass() == Slime.class) Slime.slimeHurt.play();
    }

    protected void playerPathfinderAI() {

        up    = false;
        down  = false;
        right = false;
        left  = false;

        dx = 0;
        dy = 0;

        if(detectionCollider.collisionPlayer(player)) {

            // Determine what direction the enemy will move
            if(Math.floor(player.position.y + player.getBoxCollider().getOffset().y - position.getWorldPosition().y) > 0) {
                down = true;
                currentAnimation = ANIMATION_DOWN;
            } else if(Math.floor(player.position.y + player.getBoxCollider().getOffset().y - position.getWorldPosition().y) < 0) {
                up = true;
                currentAnimation = ANIMATION_UP;
            }

            if(Math.floor(player.position.x + player.getBoxCollider().getOffset().x - position.getWorldPosition().x) > 0) {
                right = true;
                currentAnimation = ANIMATION_RIGHT;
            } else if(Math.floor(player.position.x + player.getBoxCollider().getOffset().x - position.getWorldPosition().x) < 0) {
                left = true;
                currentAnimation = ANIMATION_LEFT;
            }

            // Move the enemy in the set direction

            if(left)  dx = Math.max(-maxMoveSpeed, player.position.x + player.getBoxCollider().getOffset().x - position.getWorldPosition().x);
            if(up)    dy = Math.max(-maxMoveSpeed, player.position.y + player.getBoxCollider().getOffset().y - position.getWorldPosition().y);
            if(down)  dy = Math.min(maxMoveSpeed, player.position.y + player.getBoxCollider().getOffset().y - position.getWorldPosition().y);
            if(right) dx = Math.min(maxMoveSpeed, player.position.x + player.getBoxCollider().getOffset().x - position.getWorldPosition().x);

            isIdle = false;
        } else {
            isIdle = true;
        }
    }

    @Override
    public void update() {
        if(isDead) {
            playHurtEffect();
            onDeath();
            chapter.getEnemies().remove(this);
            this.onDestroy();
            return;
        }

        if(!Camera.shouldHandleEntity(this)) return;

        if(isInvincible) {
            dx = 0;
            dy = 0;

            if(xMoveDirection == MoveDirection.POSITIVE) {
                dx = moveBackSpeed;
            } else {
                dx = -moveBackSpeed;
            }

            if(yMoveDirection == MoveDirection.POSITIVE) {
                dy = moveBackSpeed;
            } else {
                dy = -moveBackSpeed;
            }

            totalMoveX += dx;
            totalMoveY += dy;

            if(Math.abs(totalMoveX) >= moveBackDistance && Math.abs(totalMoveY) >= moveBackDistance) isInvincible = false;
        }

        if(boxCollider.collisionPlayer(player) && !isInvincible) {
            playHitEffect();
            player.hit(damage);
            this.hit(0);
        }

        // Adjust for Delta Time
        dx *= Game.deltaTime * speedMultiplier;
        dy *= Game.deltaTime * speedMultiplier;

        if (!boxCollider.collisionTile(objectTileMap, dx, 0)) {
            position.x += dx;
        }

        if (!boxCollider.collisionTile(objectTileMap, 0, dy)) {
            position.y += dy;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if(Game.debugMode) {
            g.setColor(Color.BLUE);
            g.drawRect((int) (position.getWorldPosition().x + boxCollider.getOffset().x), (int) (position.getWorldPosition().y + boxCollider.getOffset().y),
                    boxCollider.getWidth(), boxCollider.getHeight());

            g.setColor(Color.RED);
            g.drawRect((int) (position.getWorldPosition().x + detectionCollider.getOffset().x), (int) (position.getWorldPosition().y + detectionCollider.getOffset().y),
                    detectionCollider.getWidth(), detectionCollider.getHeight());
        }
    }

    public void onDeath() {
        player.awardCurrency(rewardedCoins);
        player.awardXp(rewardedXp);
    }

    @Override
    public void hit(int damage) {
        if(damage > 0)
            super.hit(damage);

        totalMoveX = 0;
        totalMoveY = 0;

        isInvincible = true;

        if(player.position.x <= position.getWorldPosition().x) xMoveDirection = MoveDirection.POSITIVE;
        else xMoveDirection = MoveDirection.NEGATIVE;

        if(player.position.y <= position.getWorldPosition().y) yMoveDirection = MoveDirection.POSITIVE;
        else yMoveDirection = MoveDirection.NEGATIVE;
    }
}
