package com.harmony.game.object;

import com.harmony.game.entity.Entity;
import com.harmony.game.entity.Player;
import com.harmony.game.item.Item;
import com.harmony.game.utils.GUI;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.PlayerHelp;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Chest extends GameObject {

    private int item = Item.EMPTY;
    private boolean collected = false;

    private boolean isColliding = false;

    public Chest(Vector2f position, int width, int height) {
        super(position, width, height);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void update() {
        if(isColliding && !collected && Input.isKeyDown(KeyEvent.VK_E)) {
            collected = true;
            GUI.hasKey = true;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if(isColliding && !collected) {
            PlayerHelp.showLetter(g, (int) position.getWorldPosition().x - 20, (int) position.getWorldPosition().y - 20, PlayerHelp.E_ANIMATION);
            g.drawImage(GUI.collectables.getSprite(1, 0), (int) position.getWorldPosition().x +
                    ((getWidth() / 2) - 32), (int) position.getWorldPosition().y - getHeight() - 5, 64, 64, null);
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

    public int getItem() { return item; }
    public boolean isCollected() { return collected; }

    public void setItem(int item) { this.item = item; }
    public void setCollected(boolean collected) { this.collected = collected; }
}
