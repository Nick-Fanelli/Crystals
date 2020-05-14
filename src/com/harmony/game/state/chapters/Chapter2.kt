package com.harmony.game.state.chapters

import com.harmony.game.audio.BackgroundAmbience
import com.harmony.game.entity.Player
import com.harmony.game.entity.npc.NPC
import com.harmony.game.graphics.Camera
import com.harmony.game.graphics.ConsoleMessage
import com.harmony.game.graphics.Sprite
import com.harmony.game.gui.GUI
import com.harmony.game.`object`.Building
import com.harmony.game.`object`.NextLevelInvisible
import com.harmony.game.utils.Vector2f
import java.awt.*

class Chapter2 : Chapter("/tile/places/kebir-town.tmx") {

    private lateinit var message: ConsoleMessage
    private lateinit var finalMessage: ConsoleMessage

    private lateinit var nextLevelInvisible: NextLevelInvisible

    private lateinit var mrsClark: NPC

    override fun onCreate() {
        super.onCreate()

        BackgroundAmbience.playBackgroundAudio(BackgroundAmbience.OUTSIDE_AMBIENCE)

        Camera.position = Vector2f(2368f, 4967f)
        Camera.setDefaultPosition()

        player.setCurrentAnimation(Player.ANIMATION_ATTACK_UP)
        player.setIdle(true)

        GUI.showMap = false

        val playerQuestion = "Do you have a map I could have?"

        super.npcs.add(NPC(Vector2f(2795f, 1726f), "Miss. Tailor", tileManager.objectsMap,
                player, console, Sprite("/entity/npc/tailor-c2.png", 64, 64), 128, 128,
                "I'm sorry no.~Ugh. Look at him. He thinks he can just decapitate fish. Not on my watch!!!", playerQuestion))
        super.npcs.add(NPC(Vector2f(3463f, 1706f), "Mr. Salem", tileManager.objectsMap,
                player, console, Sprite("/entity/npc/fish-c2.png", 64, 64), 128, 128,
                "What? No...~Look at Miss. Tailor over there! Just because her name is Tailor doesn't mean she's a tailor!!!",
                playerQuestion))
        super.npcs.add(NPC(Vector2f(4926f, 1300f), "Mrs. Clark", tileManager.objectsMap, player, console,
                Sprite("/entity/npc/mrs-clark-c2.png", 64, 64), 128, 128,
                "Actually I accidentally bought a second. Here take it...~And remember you can press M at anytime " +
                        "to view the map completely.", playerQuestion).also { mrsClark = it })

        nextLevelInvisible = NextLevelInvisible(Vector2f(5400f, 2100f), player, 20, 350)

        super.addGameObject(Building(Vector2f(2250f, 4521f), Building.Type.TAVERN))
        super.addGameObject(Building(Vector2f(2206f, 873f), Building.Type.HUT))
        super.addGameObject(Building(Vector2f(2275f, 1550f), Building.Type.TAILOR))
        super.addGameObject(Building(Vector2f(3324f, 3863f), Building.Type.VILLA))
        super.addGameObject(Building(Vector2f(4700f, 725f), Building.Type.HOUSE))
        super.addGameObject(Building(Vector2f(3246f, 1630f), Building.Type.FISH))
        super.addGameObject(Building(Vector2f(3227f, 2733f), Building.Type.INN))

        finalMessage = ConsoleMessage(console, "According to the map, I should head down the east trail.~" +
                "The red line is my path, (Hint: I am in the top left to start).", null)

        message = ConsoleMessage(console, "I should see if anyone can give me a map.~" +
                "Then I can find-out what's happened to my world!", null)
        message.run()
    }

    override fun update() {
        super.update()
        if (mrsClark.hasTalked() && !console.isShowConsole && !GUI.showMap) {
            GUI.showMap = true
            finalMessage.run()
            nextLevelInvisible.shouldDetect = true
        }
        message.update()
        finalMessage.update()
        nextLevelInvisible.update()
    }

    override fun draw(g: Graphics2D?) {
        super.draw(g)
        nextLevelInvisible.draw(g)
    }

}