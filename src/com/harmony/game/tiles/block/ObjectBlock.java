package com.harmony.game.tiles.block;

import com.harmony.game.entity.Entity;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjectBlock extends Block {

    public ObjectBlock(BufferedImage image, Vector2f position, int width, int height) {
        super(image, position, width, height);
    }

    @Override
    public boolean update(Entity entity) {
        return false;
    }

    @Override
    public void render(Graphics2D g, int type) {
        super.render(g, TYPE_OBJECT);
    }
}
