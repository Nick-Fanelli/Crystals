package com.harmony.game.tiles.block;

import com.harmony.game.GamePanel;
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
        // TODO: ADD COLLISION
        return false;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        if(GamePanel.debugMode) {
            g.setColor(Color.WHITE);
            g.drawRect((int) position.getWorldPosition().x, (int) position.getWorldPosition().y, width, height);
        }
    }
}
