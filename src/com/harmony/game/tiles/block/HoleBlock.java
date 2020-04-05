package com.harmony.game.tiles.block;

import com.harmony.game.Game;
import com.harmony.game.entity.Entity;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HoleBlock extends Block {

    public HoleBlock(BufferedImage image, Vector2f position, int width, int height) {
        super(image, position, width, height);
    }

    @Override
    public boolean update(Entity entity) {
        // TODO: COLLISION
        return false;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        if(Game.debugMode) {
            g.setColor(Color.GREEN);
            g.drawRect((int) position.getWorldPosition().x, (int) position.getWorldPosition().y, width, height);
        }
    }
}
