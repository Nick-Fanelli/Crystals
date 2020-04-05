package com.harmony.game.tiles.block;

import com.harmony.game.entity.Entity;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Block {

    public int width;
    public int height;

    protected BufferedImage image;
    public Vector2f position;

    public Block(BufferedImage image, Vector2f position, int width, int height) {
        this.image = image;
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public abstract boolean update(Entity entity);

    public void render(Graphics2D g) {
        g.drawImage(image, (int) position.getWorldPosition().x, (int) position.getWorldPosition().y, width, height, null);
    }

    public Vector2f getTilePosition() { return new Vector2f(position.x / width, position.y / height); }

}
