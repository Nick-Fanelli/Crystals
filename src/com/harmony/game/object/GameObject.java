package com.harmony.game.object;

import com.harmony.game.entity.Player;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.state.GameStateManager;
import com.harmony.game.utils.Vector2f;
import org.w3c.dom.css.Rect;

import java.awt.*;

public abstract class GameObject {

    public final Vector2f position;
    private final int width;
    private final int height;
    private Rectangle rectangle;

    public GameObject(Vector2f position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.rectangle = new Rectangle((int) position.x - 5, (int) position.y - 5, width + 10, height + 10);

        GameStateManager.getCurrentState().addGameObject(this);
    }

    public int getWidth()  { return width  ; }
    public int getHeight() { return height ; }

    public boolean isCollidingWith(Player player) {
       return player.getBoxCollider().getBoundsAsAbsRect().intersects(new Rectangle((int) position.getWorldPosition().x - 10,
                (int) position.getWorldPosition().y - 10, width + 20, height + 20));
    }

    public abstract void onCreate();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void onDestroy();
}
