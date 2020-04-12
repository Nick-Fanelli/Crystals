package com.harmony.game.item.drops;

import com.harmony.game.entity.Player;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public abstract class Drop {

    public Vector2f position;
    public Rectangle rectangle;

    public Drop(Vector2f position, int width, int height) {
        this.position = position;
        this.rectangle = position.toRectangle(width, height);
    }

    public abstract void update(Player player);
    public abstract void draw(Graphics2D g);

}
