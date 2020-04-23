package com.harmony.game.object;

import com.harmony.game.Game;
import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Camera;
import com.harmony.game.state.GameStateManager;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class NextLevelInvisible extends GameObject {

    private Player player;
    public boolean shouldDetect = false;

    public NextLevelInvisible(Vector2f position, Player player, int width, int height) {
        super(position, width, height);

        this.player = player;
    }

    @Override public void onCreate() {}

    @Override
    public void update() {
        if(!shouldDetect) return;
        if(!Camera.shouldHandleGameObject(this)) return;
        if(isCollidingWith(player)) GameStateManager.nextLevel();
    }

    @Override
    public void draw(Graphics2D g) {
        if(!shouldDetect) return;
        if(!Camera.shouldHandleGameObject(this)) return;
        if(Game.debugMode) {
            g.drawRect((int) position.getWorldPosition().x, (int) position.getWorldPosition().y, getWidth(), getHeight());
        }
    }

    @Override public void onDestroy() {}
}
