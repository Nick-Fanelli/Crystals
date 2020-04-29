package com.harmony.game.physics.collision;

import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Camera;
import com.harmony.game.object.GameObject;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MeshCollider {

    private GameObject object;
    private Vector2f offset;
    private Mesh mesh;

    public MeshCollider(GameObject object, Vector2f offset, BufferedImage image) {
        this(object, offset, new Mesh(image));
    }

    public MeshCollider(GameObject object, Vector2f offset, Mesh mesh) {
        this.object = object;
        this.offset = offset;
        this.mesh = mesh;
    }

    public boolean isCompleteCollision(Player player, float dx, float dy) {
        if(!isColliding(player)) return false;

        for(int c = 0; c < 4; c++) {

            int ex = (int) ((player.position.x + dx + Camera.position.x) + (c % 2) * player.getBoxCollider().getWidth()  + offset.x) - (int) object.position.x + 35;
            int ey = (int) ((player.position.y + dy + Camera.position.y) + (c / 2) * player.getBoxCollider().getHeight() + offset.y) - (int) object.position.y + 88;

            if(ex < 0) ex = 0;
            if(ey < 0) ey = 0;

            try { if(mesh.isCollidable(ex, ey)) return true; }
            catch(Exception e) {}
        }

        return false;
    }

    public boolean isColliding(Player player) {
        Rectangle pRect = new Rectangle((int) player.position.add(Camera.position).x +
                (int) player.getBoxCollider().getOffset().x + (int) player.dx, (int) player.position.add(Camera.position).y +
                (int) player.getBoxCollider().getOffset().y + (int) player.dy,
                player.getBoxCollider().getWidth(), player.getBoxCollider().getHeight());
        return pRect.intersects(new Rectangle((int) (object.position.x + offset.x),
                (int) (object.position.y + offset.y), object.getWidth(), object.getHeight()));
    }

    public Mesh getMesh() { return mesh; }
    public Vector2f getOffset() { return offset; }

}
