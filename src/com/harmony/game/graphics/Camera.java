package com.harmony.game.graphics;

import com.harmony.game.Game;
import com.harmony.game.entity.Entity;
import com.harmony.game.object.GameObject;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.tiles.TileManager;
import com.harmony.game.tiles.block.Block;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public final class Camera {

    private static BoxCollider collisionCam;
    private static boolean created = false;

    public static Vector2f position = new Vector2f(0, 0);

    public Camera(BoxCollider collisionCam) {
        Camera.collisionCam = collisionCam;
        created = true;
    }

    public static void update() {
        if (!created) return;
    }

    public static boolean shouldHandleTile(Block block) {
        if (!created) {
            System.err.println("Must create the camera first before asking to show tile");
            return false;
        }

        if (block.getAbsPosition().getWorldPosition().x + block.getWidth() >= collisionCam.getOffset().x)
            if (block.getAbsPosition().getWorldPosition().x + block.getWidth() <= Display.width - collisionCam.getOffset().x + block.getWidth())
                if (block.getAbsPosition().getWorldPosition().y + block.getHeight() >= collisionCam.getOffset().y)
                    if (block.getAbsPosition().getWorldPosition().y <= Display.height - collisionCam.getOffset().y)
                        return true;

        return false;
    }

    public static boolean shouldHandleGameObject(GameObject object) {
        if (!created) {
            System.err.println("Must create the camera first before asking to show game object");
            return false;
        }

        if (object.position.x >= (position.x + collisionCam.getOffset().x) - object.getWidth())
            if (object.position.x <= position.x + collisionCam.getWidth() + collisionCam.getOffset().x)
                if (object.position.y >= (position.y + collisionCam.getOffset().y) - object.getHeight())
                    if (object.position.y <= position.y + collisionCam.getHeight() + collisionCam.getOffset().y)
                        return true;

        return false;
    }

    public static boolean shouldHandleEntity(Entity entity) {
        if (!created) {
            System.err.println("Must create the camera first before asking to show entity");
            return false;
        }

        if (entity.position.x >= (position.x + collisionCam.getOffset().x) - entity.width)
            if (entity.position.x <= position.x + collisionCam.getWidth() + collisionCam.getOffset().x)
                if (entity.position.y >= (position.y + collisionCam.getOffset().y) - entity.height)
                    if (entity.position.y <= position.y + collisionCam.getHeight() + collisionCam.getOffset().y)
                        return true;

        return false;
    }

    public static void draw(Graphics2D g) {
        if (!created) return;
        if (Game.debugMode) {
            g.setColor(Color.YELLOW);
            g.drawRect((int) collisionCam.getOffset().x, (int) collisionCam.getOffset().y,
                    collisionCam.getWidth(), collisionCam.getHeight());
        }
    }
}