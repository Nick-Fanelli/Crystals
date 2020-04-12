package com.harmony.game.item.drops;

import com.harmony.game.entity.Player;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public abstract class Drop {
    protected Vector2f position;

    public Drop(Vector2f position) { this.position = position; }

    public abstract void update(Player player);
    public abstract void draw(Graphics2D g);
}
