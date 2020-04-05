package com.harmony.game.utils;

import com.harmony.game.graphics.Camera;

public class Vector2f {

    public float x;
    public float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f() {
        this(0, 0);
    }

    public Vector2f getWorldPosition() {
        return new Vector2f(this.x - Camera.position.x, this.y - Camera.position.y);
    }

}
