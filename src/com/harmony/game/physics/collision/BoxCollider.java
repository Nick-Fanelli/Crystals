package com.harmony.game.physics.collision;

import com.harmony.game.entity.Entity;
import com.harmony.game.utils.Vector2f;

public class BoxCollider {

    private Entity entity;
    private Vector2f offset;
    private int width;
    private int height;

    public BoxCollider(Entity entity, Vector2f offset, int width, int height) {
        this.entity = entity;
        this.offset = offset;
        this.width = width;
        this.height = height;
    }

    public void update() {
        
    }

    public Vector2f getOffset() {
        return offset;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
