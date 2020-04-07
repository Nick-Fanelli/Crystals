package com.harmony.game.object;

import com.harmony.game.entity.Entity;
import com.harmony.game.entity.Player;
import com.harmony.game.utils.GUI;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.PlayerHelp;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Door extends GameObject {

    private boolean isColliding = false;

    public Door(Vector2f position, int width, int height) {
        super(position, width, height);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void update() {
        if(isColliding && GUI.hasKey && Input.isKeyDown(KeyEvent.VK_E)) {
            System.out.println("Move On");
            GUI.hasKey = false;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if(isColliding && GUI.hasKey) {
            PlayerHelp.showLetter(g, (int) position.getWorldPosition().x - 20, (int) position.getWorldPosition().y - 20, PlayerHelp.E_ANIMATION);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean isCollidingWith(Entity entity) {
        if(!(entity instanceof Player)) return super.isCollidingWith(entity);

        if(!super.isCollidingWith(entity)) { isColliding = false; return false; }
        isColliding = true;

        return true;
    }
}
