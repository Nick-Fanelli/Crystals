package com.harmony.game.tiles.block;

import com.harmony.game.Game;
import com.harmony.game.entity.Entity;
import com.harmony.game.graphics.Camera;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Block {

    public static final int CHEST_TILE_POSITION = 4097;
    public static final int LEVEL_DOOR_POSITION = 170;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HOLE   = 1;
    public static final int TYPE_OBJECT = 2;

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

    public void render(Graphics2D g, int type) {
        if(Camera.shouldHandleTile(this)) {
            g.drawImage(image, (int) position.getWorldPosition().x, (int) position.getWorldPosition().y, width, height, null);
            if(Game.debugMode) {
                Color color;
                if(type == TYPE_OBJECT) {
                    color = Color.WHITE;
                } else if(type == TYPE_HOLE) {
                    color = Color.GREEN;
                } else {
                    return;
                }
                g.setColor(color);
                g.drawRect((int) position.getWorldPosition().x, (int) position.getWorldPosition().y, width, height);
            }
        }
    }

    public Vector2f getTilePosition() { return new Vector2f(position.x / width, position.y / height); }
    public Vector2f getAbsPosition() { return position; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

}
