package com.harmony.game.state.chapters;

import com.harmony.game.graphics.Camera;
import com.harmony.game.object.Building;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Chapter3 extends Chapter {

    public Chapter3() {
        super("/tile/places/kebir-town.tmx");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Camera.position = new Vector2f(2000, 2000);

        super.addGameObject(new Building(new Vector2f(3380, 2574), Building.Type.HUT));
        super.addGameObject(new Building(new Vector2f(3309, 1560), Building.Type.VILLA));
    }

    @Override
    public void update() {
        super.update();

        player.printPosition();
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
