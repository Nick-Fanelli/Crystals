package com.harmony.game.state;

import java.awt.*;

public abstract class State {

    public void onCreate() {}
    public void update() {}
    public void draw(Graphics2D g) {}
    public void onDestroy() {}

}
