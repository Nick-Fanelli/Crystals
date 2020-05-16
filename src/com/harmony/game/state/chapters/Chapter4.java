package com.harmony.game.state.chapters;

import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Camera;
import com.harmony.game.item.Item;
import com.harmony.game.object.Chest;
import com.harmony.game.tiles.block.Block;
import com.harmony.game.utils.Vector2f;

public class Chapter4 extends Chapter {

    public Chapter4() { super("/tile/places/spider-cave-c4.tmx"); }

    public void onCreate() {
        Block.CHEST_TILE_POSITION = 254;

        super.onCreate();

        Camera.position = new Vector2f(-448, 8500);
        Camera.setDefaultPosition();

        player.setCurrentAnimation(Player.ANIMATION_UP);

        for(Chest chest : super.chests) {
            if(chest.position.equals(128, 8576)) chest.setItem(Item.XP_10);
            else if(chest.position.equals(2112, 8832)) chest.setItem(Item.HEALTH_POINT);
        }
    }

    public void update() {
        super.update();
    }

}
