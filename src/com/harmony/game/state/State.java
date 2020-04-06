package com.harmony.game.state;

import com.harmony.game.object.GameObject;

import java.awt.*;
import java.util.ArrayList;

public abstract class State {

    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    public void onCreate() {}

    public void update() { for(GameObject object : gameObjects) object.update(); }

    public void draw(Graphics2D g) { for(GameObject object : gameObjects) object.draw(g); }

    public void onDestroy() {}

    public void addGameObject(GameObject object) {
        object.onCreate();
        gameObjects.add(object);
    }

    public void removeGameObject(GameObject object) {
        if(!gameObjects.contains(object)) return;
        object.onDestroy();
        gameObjects.remove(object);
    }

    public ArrayList<GameObject> getGameObjects() { return gameObjects; }
}
