package com.harmony.game.state.chapters

import com.harmony.game.graphics.Camera
import com.harmony.game.utils.Vector2f

class Chapter4 : Chapter("/tile/places/spider-cave-c4.tmx") {

    override fun onCreate() {
        super.onCreate()

        Camera.position = Vector2f(-448f, 9059f)
        Camera.setDefaultPosition()
    }

    override fun update() {
        super.update()
    }

}