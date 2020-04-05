package com.harmony.game.tiles;

import com.harmony.game.graphics.Sprite;
import com.harmony.game.tiles.block.Block;
import com.harmony.game.tiles.block.NormalBlock;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class NormalTileMap extends TileMap {

    private ArrayList<Block> blocks;

    public NormalTileMap(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        blocks = new ArrayList<>();

        String[] block = data.split(",");
        for(int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if(temp != 0) {
                blocks.add(new NormalBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), ((temp - 1) / tileColumns)),
                        new Vector2f((i % width) * tileWidth, (i / height) * tileHeight), tileWidth, tileHeight));
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        for(int i = 0; i < blocks.size(); i++) {
            blocks.get(i).render(g);
        }
    }
}
