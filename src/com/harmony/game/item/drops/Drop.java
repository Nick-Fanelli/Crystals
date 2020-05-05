package com.harmony.game.item.drops;

import com.harmony.game.entity.Player;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public abstract class Drop {

    public Vector2f position;
    public Rectangle rectangle;

    protected final int width;
    protected final int height;

    public Drop(Vector2f position, int width, int height) {
        this.position = position;
        this.rectangle = position.toRectangle(width, height);

        this.width = width;
        this.height = height;
    }

    public boolean collisionWithPlayer(Player player) {
        return player.getRectangle().intersects
                (new Rectangle((int) position.getWorldPosition().x, (int) position.getWorldPosition().y, width, height));
    }

    public abstract void update(Player player);
    public abstract void draw(Graphics2D g);

}
