package com.harmony.game.object;

import com.harmony.game.entity.Entity;
import com.harmony.game.state.GameStateManager;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public abstract class GameObject {

    public final Vector2f position;
    private final int width;
    private final int height;

    public GameObject(Vector2f position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;

         GameStateManager.getCurrentState().addGameObject(this);
    }

    public int getWidth()  { return width  ; }
    public int getHeight() { return height ; }

    public boolean isCollidingWith(Entity entity) {
        return entity.getBoxCollider().getBoundsAsAbsRect().intersects(new Rectangle((int) position.getWorldPosition().x, (int) position.getWorldPosition().y, width, height));
    }

    public abstract void onCreate();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void onDestroy();
}
