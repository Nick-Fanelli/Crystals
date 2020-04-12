package com.harmony.game.item;

import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Camera;
import com.harmony.game.item.drops.Drop;
import com.harmony.game.item.drops.HealthPointDrop;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Drops {

    public static ArrayList<Drop> drops = new ArrayList<>();

    public static final int DROP_HEALTH_POINT = 0;

    public static void drop(Vector2f position, int drop) {
        if(drop == DROP_HEALTH_POINT) drops.add(new HealthPointDrop(position));
    }

    public static void update(Player player) {
        for(int i = 0; i < drops.size(); i++) {
            if(Camera.shouldHandleDrop(drops.get(i))) drops.get(i).update(player);
        }
    }


    public static void draw(Graphics2D g) {
        for(Drop drop : drops) if(Camera.shouldHandleDrop(drop)) drop.draw(g);
    }

    public static void remove(Drop drop) { drops.remove(drop); }

}
