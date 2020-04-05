package com.harmony.game.tiles;

import com.harmony.game.graphics.Sprite;
import com.harmony.game.tiles.block.Block;
import com.harmony.game.tiles.block.HoleBlock;
import com.harmony.game.tiles.block.ObjectBlock;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class ObjectTileMap extends TileMap {

    private ArrayList<Block> blocks;
    private ArrayList<Block> bounds;

    public ObjectTileMap(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        Block tempBlock;
        blocks = new ArrayList<>();
        bounds = new ArrayList<>();

        String[] block = data.split(",");
        for(int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if(temp != 0) {
                if(temp == 172) {
                    tempBlock = new HoleBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), ((temp - 1) / tileColumns)),
                            new Vector2f((i % width) * tileWidth, (i / height) * tileHeight), tileWidth, tileHeight);
                } else {
                    tempBlock = new ObjectBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), ((temp - 1) / tileColumns)),
                            new Vector2f((i % width) * tileWidth, (i / height) * tileHeight), tileWidth, tileHeight);
                    bounds.add(tempBlock);
                }
                blocks.add(tempBlock);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        for(int i = 0; i < blocks.size(); i++) {
            blocks.get(i).render(g);
        }
    }

    public ArrayList<Block> getBlocks() { return blocks; }
    public ArrayList<Block> getBounds() { return bounds; }
}
