package com.harmony.game.object;

import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Camera;
import com.harmony.game.item.Item;
import com.harmony.game.state.GameStateManager;
import com.harmony.game.state.chapters.Chapter;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.PlayerHelp;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Chest extends GameObject {

    private int item = Item.EMPTY;
    private boolean collected = false;

    private boolean isColliding = false;
    private Player player;

    public Chest(Vector2f position, int width, int height) {
        super(position, width, height, false);

        ((Chapter) GameStateManager.getCurrentState()).addChest(this);
    }

    @Override
    public void onCreate() {}

    @Override
    public void update() {
        if(!Camera.shouldHandleGameObject(this)) return;

        if(isColliding && !collected && Input.isKeyDown(KeyEvent.VK_Q) && player != null) {
            Item.givePlayer(player, item);
            collected = true;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if(!Camera.shouldHandleGameObject(this)) return;

        if(isColliding && !collected && player != null) {
            PlayerHelp.showLetter(g, (int) (position.getWorldPosition().x - 20) +
                            (player.position.x <= position.getWorldPosition().x ? getWidth() : 0),
                    (int) position.getWorldPosition().y - 20, PlayerHelp.Q_ANIMATION);
            Item.displayItem(g, item, (int) position.getWorldPosition().x, (int) position.getWorldPosition().y - 40, getWidth());
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean isCollidingWith(Player player) {
        if(this.player == null) this.player = player;
        if(!super.isCollidingWith(player)) { isColliding = false; return false; }

        return isColliding = true;
    }

    public int getItem() { return item; }
    public boolean isCollected() { return collected; }

    public void setItem(int item) { this.item = item; }
    public void setCollected(boolean collected) { this.collected = collected; }
}
