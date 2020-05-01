package com.harmony.game.tiles;

import com.harmony.game.graphics.Sprite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.util.ArrayList;

public class TileManager {

    public static ArrayList<TileMap> tileMaps;

    public TileManager() {
        tileMaps = new ArrayList<>();
    }

    public TileManager(String path) {
        tileMaps = new ArrayList<>();
        addTileMap(path, 64, 64);
    }

    private void addTileMap(String path, int blockWidth, int blockHeight) {
        String imagePath;

        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileColumns;
        int layers;
        Sprite sprite;

        String[] data = new String[10];

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            System.out.println("Loading Tile Map: " + path);
            Document doc = builder.parse(TileManager.class.getResourceAsStream(path));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element eElement = (Element) node;

            imagePath = eElement.getAttribute("name");
            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
            tileColumns = Integer.parseInt(eElement.getAttribute("columns"));
            sprite = new Sprite("/tile/" + imagePath + ".png", tileWidth, tileHeight);

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for(int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if(i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }

                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

                if(i >= 1) {
                    tileMaps.add(new NormalTileMap(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                } else {
                    tileMaps.add(new ObjectTileMap(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                }
            }

        } catch(Exception e) {
            System.err.println("Crystals: Tile Manager");
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        for(int i = 0; i < tileMaps.size(); i++) {
            tileMaps.get(i).draw(g);
        }
    }

    public ObjectTileMap getObjectsMap() { return (ObjectTileMap) tileMaps.get(0); }
}
