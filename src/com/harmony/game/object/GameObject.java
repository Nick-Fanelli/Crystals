package com.harmony.game.object;

import com.harmony.game.entity.Player;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.state.GameStateManager;
import com.harmony.game.utils.Vector2f;
import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;

public abstract class GameObject {

    public final Vector2f position;
    private final int width;
    private final int height;
    private Rectangle rectangle;

    protected boolean isCollideable = false;

    public GameObject(Vector2f position, int width, int height, boolean addToCurrentState) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.rectangle = new Rectangle((int) position.x - 5, (int) position.y - 5, width + 10, height + 10);

        if(addToCurrentState) GameStateManager.getCurrentState().addGameObject(this);
    }

    public int getWidth()  { return width  ; }
    public int getHeight() { return height ; }
    public boolean isCollideable() { return isCollideable; }

    public boolean isCollidingWith(Player player) {
       return player.getBoxCollider().getBoundsAsAbsRect().intersects(new Rectangle((int) position.getWorldPosition().x - 10,
                (int) position.getWorldPosition().y - 10, width + 20, height + 20));
    }

    public boolean isCollidingWithFuture(Player player, float dx, float dy) {
        Rectangle rectangle = player.getBoxCollider().getBoundsAsAbsRect();
        rectangle.x += player.dx;
        rectangle.y += player.dy;

        return rectangle.intersects(new Rectangle((int) position.getWorldPosition().x,
                (int) position.getWorldPosition().y, width, height));
    }

    public abstract void onCreate();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void onDestroy();
}
