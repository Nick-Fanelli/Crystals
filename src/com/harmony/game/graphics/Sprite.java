package com.harmony.game.graphics;

import com.harmony.game.utils.ImageUtils;

import java.awt.image.BufferedImage;

public class Sprite {

    private BufferedImage sheet;
    private int sheetWidth;
    private int sheetHeight;

    private int tileWidth;
    private int tileHeight;

    private int numColumns;
    private int numRows;

    private BufferedImage[] sprites;

    public Sprite(BufferedImage sheet, int tileWidth, int tileHeight) {
        this.sheet = sheet;

        this.sheetWidth = sheet.getWidth();
        this.sheetHeight = sheet.getHeight();

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        numColumns = sheetWidth  / tileWidth  ;
        numRows    = sheetHeight / tileHeight ;

        sprites = new BufferedImage[numRows * numColumns];

        for(int x = 0; x < numColumns; x++) {
            for(int y = 0; y < numRows; y++) {
                sprites[x + y * numColumns] = this.sheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
            }
        }
    }

    public Sprite(String path, int tileWidth, int tileHeight) {
        this(ImageUtils.loadImage(path), tileWidth, tileHeight);
    }

    public int getSheetWidth() { return sheetWidth; }
    public int getSheetHeight() { return sheetHeight; }
    public int getTileWidth() { return tileWidth; }
    public int getTileHeight() { return tileHeight; }
    public int getNumColumns() { return numColumns; }
    public int getNumRows() { return numRows; }
    public BufferedImage getSpriteSheet() { return sheet; }
    public BufferedImage[] getSprites() { return sprites; }

    public BufferedImage getSprite(int col, int row) { return sprites[col + row * numColumns]; }
    public BufferedImage getSprite(int i) { return sprites[i]; }
}
