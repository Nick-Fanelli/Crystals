package com.harmony.game.state.chapters

import com.harmony.game.`object`.Building
import com.harmony.game.`object`.NextLevelInvisible
import com.harmony.game.audio.BackgroundAmbience
import com.harmony.game.entity.enemy.Slime
import com.harmony.game.graphics.Camera
import com.harmony.game.graphics.ConsoleMessage
import com.harmony.game.item.Drops
import com.harmony.game.item.Item
import com.harmony.game.utils.Vector2f
import java.awt.Graphics2D

class Chapter3 : Chapter("/tile/places/forest-c3.tmx") {

    private lateinit var nextLevelInvisible: NextLevelInvisible

    private lateinit var message: ConsoleMessage

    override fun onCreate() {
        Camera.position = Vector2f(968f, 487f)
        Camera.setDefaultPosition()
        super.onCreate()

        BackgroundAmbience.playBackgroundAudio(BackgroundAmbience.OUTSIDE_AMBIENCE)

        message = ConsoleMessage(console, "I think there's one of those slimes up ahead...~Use the space-bar while facing " +
                "the slime to attack.~Good Luck...", null)
        message!!.run()

        super.addGameObject(Building(Vector2f(3380f, 2574f), Building.Type.HUT))
        super.addGameObject(Building(Vector2f(3309f, 1560f), Building.Type.VILLA))
        super.enemies.add(Slime(Vector2f(2392f, 887f), this, player, tileManager.objectsMap,
                64, 64))
        super.enemies.add(Slime(Vector2f(3717f, 844f), this, player, tileManager.objectsMap,
                64, 64))
        super.enemies.add(Slime(Vector2f(6212f, 885f), this, player, tileManager.objectsMap,
                64, 64))
        super.enemies.add(Slime(Vector2f(5605f, 6565f), this, player, tileManager.objectsMap,
                64, 64))

        Drops.drop(Vector2f(5170f, 805f), Drops.DROP_HEALTH_POINT)
        Drops.drop(Vector2f(3076f, 830f), Drops.DROP_HEALTH_POINT)

        for (chest in chests) {
            chest.item = Item.CURRENCY_10
        }

        nextLevelInvisible = NextLevelInvisible(Vector2f(7819f, 7244f), player, 96, 140)
        nextLevelInvisible.shouldDetect = true
    }

    override fun update() {
        super.update()

        message.update()
        nextLevelInvisible.update()

        player.printPosition()
    }

    override fun draw(g: Graphics2D?) {
        super.draw(g)
        nextLevelInvisible.draw(g)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}