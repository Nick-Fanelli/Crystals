package com.harmony.game.entity;

import com.harmony.game.GamePanel;
import com.harmony.game.graphics.Animation;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {

    public static final int ANIMATION_RIGHT = 0;
    public static final int ANIMATION_LEFT  = 1;
    public static final int ANIMATION_DOWN  = 2;
    public static final int ANIMATION_UP    = 3;
    public static final int ANIMATION_FALL  = 4;
    public static final int ANIMATION_ATTACK_RIGHT = 5;
    public static final int ANIMATION_ATTACK_LEFT  = 6;
    public static final int ANIMATION_ATTACK_DOWN  = 7;
    public static final int ANIMATION_ATTACK_UP    = 8;

    private int currentAnimation;

    public Player() {
        super(new Vector2f((GamePanel.width / 2f) - 32, (GamePanel.height / 2f) - 32), 64, 64);

        this.maxMoveSpeed = 4f;
        this.acceleration = 3f;

        this.health = 10;
        this.damage = 2;

        boxCollider = new BoxCollider(this, new Vector2f(12, 40), 42, 20);

        sprite = new Sprite("/entity/player.png", 32, 32);
        animation = new Animation(sprite);

        isIdle = false;
    }

    @Override
    public void onCreate() {
        currentAnimation = ANIMATION_RIGHT;
    }

    @Override
    public void update() {
        up     = Input.isKey(KeyEvent.VK_W);
        left   = Input.isKey(KeyEvent.VK_A);
        down   = Input.isKey(KeyEvent.VK_S);
        right  = Input.isKey(KeyEvent.VK_D);

        if (Input.isKeyDown(KeyEvent.VK_W)) currentAnimation = ANIMATION_UP;
        else if (Input.isKeyDown(KeyEvent.VK_A)) currentAnimation = ANIMATION_LEFT;
        else if (Input.isKeyDown(KeyEvent.VK_S)) currentAnimation = ANIMATION_DOWN;
        else if (Input.isKeyDown(KeyEvent.VK_D)) currentAnimation = ANIMATION_RIGHT;
        else { isIdle = true; }

        if(up) {
            dy -= acceleration;
            if(dy < -maxMoveSpeed) dy = -maxMoveSpeed;
        } else if(down) {
            dy += acceleration;
            if(dy > maxMoveSpeed) dy = maxMoveSpeed;
        } else {
            dy = 0;
        }

        if(right) {
            dx += acceleration;
            if(dx > maxMoveSpeed) dx = maxMoveSpeed;
        } else if(left) {
            dx -= acceleration;
            if(dx < -maxMoveSpeed) dx = -maxMoveSpeed;
        } else {
            dx = 0;
        }

        if(up || down || right || left) isIdle = false;
        else { dy = 0; dx = 0; }

        // Adjust for Delta Time
        dx *= GamePanel.deltaTime * speedMultiplier;
        dy *= GamePanel.deltaTime * speedMultiplier;

        boxCollider.update();

        Camera.position.x += dx;
        Camera.position.y += dy;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(animation.animate(currentAnimation, getDelay(currentAnimation)), (int) position.x, (int) position.y,
                width, height, null);
        if(GamePanel.debugMode) {
            g.setColor(Color.BLUE);
            g.drawRect((int) (position.x + boxCollider.getOffset().x), (int) (position.y + boxCollider.getOffset().y),
                    boxCollider.getWidth(), boxCollider.getHeight());
        }
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
        if(animation == ANIMATION_FALL) return 350;
        return 0;
    }

}
