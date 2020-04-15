package com.harmony.game.entity.enemy;

import com.harmony.game.Game;
import com.harmony.game.entity.Entity;
import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Camera;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.state.chapters.Chapter;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.Vector2f;

public abstract class Enemy extends Entity  {

    protected Player player;

    protected BoxCollider detectionCollider;

    private Chapter chapter;

    public Enemy(Vector2f position, Chapter chapter, Player player, ObjectTileMap objectTileMap, int width, int height) {
        super(position, objectTileMap, width, height);

        this.chapter = chapter;
        this.player = player;
    }

    private void playHitEffect() {
        if(getClass() == Slime.class) Slime.slimeEffect.play();
    }

    @Override
    public void update() {
        if(isDead) {
            playHitEffect();
            chapter.getEnemies().remove(this);
            return;
        }
        if(!Camera.shouldHandleEntity(this)) return;

        if(boxCollider.collisionPlayer(player)) {
            playHitEffect();
            player.hit(damage);
            this.hit(0);
        }

        // Adjust for Delta Time
        dx *= Game.deltaTime * speedMultiplier;
        dy *= Game.deltaTime * speedMultiplier;

        if (!boxCollider.collisionTile(objectTileMap, dx, 0)) {
            position.x += dx;
        }

        if (!boxCollider.collisionTile(objectTileMap, 0, dy)) {
            position.y += dy;
        }

    }

    @Override
    public void hit(int damage) {
        if(damage > 0)
            super.hit(damage);
        if(player.position.y <= position.getWorldPosition().y) dy += 130;
        else if(player.position.y > position.getWorldPosition().y)  dy -= 130;
        if(player.position.x <= position.getWorldPosition().x) dx += 130;
        else if(player.position.x > position.getWorldPosition().x)  dx -= 130;
    }
}
