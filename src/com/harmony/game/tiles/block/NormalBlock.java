package com.harmony.game.tiles.block;

import com.harmony.game.entity.Entity;
import com.harmony.game.utils.Vector2f;

import java.awt.image.BufferedImage;

public class NormalBlock extends Block {

    public NormalBlock(BufferedImage image, Vector2f position, int width, int height) {
        super(image, position, width, height);
    }

    @Override
    public boolean update(Entity entity) {
        return false;
    }
}
