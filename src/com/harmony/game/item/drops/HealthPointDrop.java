package com.harmony.game.item.drops;

import com.harmony.game.entity.Player;
import com.harmony.game.utils.ImageUtils;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthPointDrop extends Drop {

    private static final BufferedImage image = ImageUtils.loadImage("/item/health_points.png")
            .getSubimage(0, 0, 11, 10);

    public HealthPointDrop(Vector2f position) { super(position); }

    @Override
    public void update(Player player) {

    }

    @Override
    public void draw(Graphics2D g) {

    }
}
