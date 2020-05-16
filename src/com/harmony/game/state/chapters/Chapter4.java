package com.harmony.game.state.chapters;

import com.harmony.game.graphics.Camera;
import com.harmony.game.utils.Vector2f;

public class Chapter4 extends Chapter {

    public void onCreate() {
        super.onCreate();
        Camera.position = new Vector2f(-448.0F, 9059.0F);
        Camera.setDefaultPosition();
    }

    public void update() {
        super.update();
    }

    public Chapter4() {
        super("/tile/places/spider-cave-c4.tmx");
    }
}
