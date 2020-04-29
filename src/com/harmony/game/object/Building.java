package com.harmony.game.object;

import com.harmony.game.entity.Player;
import com.harmony.game.physics.collision.MeshCollider;
import com.harmony.game.utils.ImageUtils;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Building extends GameObject {

    public Type type;

    public enum Type {
        TAVERN("/buildings/village/tavern.png", 640, 790, null),
        HUT("/buildings/village/hut.png", 640, 595, null),
        TAILOR("/buildings/village/tailor.png", 640, 440, null),
        VILLA("/buildings/village/villa.png", 1155, 950, null),
        HOUSE("/buildings/village/house.png", 768, 744, null),
        FISH("/buildings/village/fish.png", 640, 360, null),
        INN("/buildings/village/inn.png", 640, 790, null);

        private final BufferedImage image;
        private final int width;
        private final int height;
        private final String tilemap;
        private final boolean isEnterable;

        Type(String path, int width, int height, String tilemap) {
            this.image = ImageUtils.getScaledImage(ImageUtils.loadImage(path), width, height);
            this.width = width;
            this.height = height;
            this.tilemap = tilemap;
            isEnterable = tilemap != null;
        }
    }

    private final MeshCollider meshCollider;

    public Building(Vector2f position, Type type) {
        super(position, type.width, type.height);

        this.type = type;
        this.isCollideable = true;

        this.meshCollider = new MeshCollider(this, new Vector2f(), type.image);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(type.image, (int) position.getWorldPosition().x, (int) position.getWorldPosition().y, getWidth(), getHeight(), null);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean isCollidingWithFuture(Player player, float x, float y) {
        return meshCollider.isCompleteCollision(player, x, y);
    }
}
