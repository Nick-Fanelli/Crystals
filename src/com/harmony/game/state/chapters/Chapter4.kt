package com.harmony.game.state.chapters

import com.harmony.game.entity.enemy.Spider
import com.harmony.game.graphics.Camera
import com.harmony.game.utils.Vector2f

class Chapter4 : Chapter("/tile/places/forest-c3.tmx") {

    override fun onCreate() {
        super.onCreate()

        Camera.position = Vector2f(968f, 487f)

        super.enemies.add(Spider(Vector2f(1568f, 850f), this, player, tileManager.objectsMap))
    }

}