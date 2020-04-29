package com.harmony.game.utils;

import com.harmony.game.graphics.Camera;
import com.harmony.game.physics.collision.BoxCollider;

import java.awt.*;

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

    public Vector2f getWorldPosition() { return new Vector2f(this.x - Camera.position.x, this.y - Camera.position.y); }

    public void reset() {
        this.x = 0;
        this.y = 0;
    }

    public Rectangle toRectangle(int width, int height) { return new Rectangle((int) x, (int) y, width, height); }

    @Override public String toString() { return "X: " + x + ", Y: " + y; }
    @Override public boolean equals(Object obj) { return ((Vector2f) obj).x == this.x && ((Vector2f) obj).y == this.y; }

    public Vector2f add(float x, float y) { return new Vector2f(this.x + x, this.y + y); }
    public Vector2f sub(float x, float y) { return new Vector2f(this.x - x, this.y - y); }
    public Vector2f mul(float x, float y) { return new Vector2f(this.x * x, this.y * y); }
    public Vector2f div(float x, float y) { return new Vector2f(this.x / x, this.y / y); }

    public Vector2f add(Vector2f r) { return new Vector2f(r.x + this.x, r.y + this.y); }
    public Vector2f sub(Vector2f r) { return new Vector2f(r.x - this.x, r.y - this.y); }
    public Vector2f mul(Vector2f r) { return new Vector2f(r.x * this.x, r.y * this.y); }
    public Vector2f div(Vector2f r) { return new Vector2f(r.x / this.x, r.y / this.y); }

    public Vector2f inverse() { return this.mul(-1, -1); }

    public static float toRelativeX(int x) { return x - Camera.position.x; }
    public static float toRelativeY(int y) { return y - Camera.position.y; }
}
