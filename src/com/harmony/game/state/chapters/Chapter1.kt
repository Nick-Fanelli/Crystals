package com.harmony.game.state.chapters

import com.harmony.game.`object`.NextLevelInvisible
import com.harmony.game.animation.controller.C1Controller
import com.harmony.game.animation.scene.Chapter1Scene1
import com.harmony.game.audio.BackgroundAmbience
import com.harmony.game.entity.npc.NPC
import com.harmony.game.graphics.Camera
import com.harmony.game.graphics.ConsoleMessage
import com.harmony.game.graphics.Sprite
import com.harmony.game.gui.GUI
import com.harmony.game.state.GameStateManager
import com.harmony.game.utils.Vector2f
import java.awt.Graphics2D

class Chapter1 : Chapter("/tile/places/kebir.tmx") {

    private lateinit var message: ConsoleMessage

    private lateinit var npcMrCrow: NPC
    private lateinit var npcAmber: NPC
    private lateinit var npcMrsCaren: NPC
    private lateinit var matt: NPC

    private lateinit var nextLevelInvisible: NextLevelInvisible

    private var stage1Done = false
    private var displayedCutScene = false
    private var stage2Done = false

    private var controller: C1Controller? = null

    override fun onCreate() {
        super.onCreate()

        GameStateManager.showCutScene(Chapter1Scene1());

        BackgroundAmbience.playBackgroundAudio(BackgroundAmbience.OUTSIDE_AMBIENCE)

        handleNPC()

        GUI.showMap = false

        Camera.position = Vector2f(1000f, 1000f)
        Camera.setDefaultPosition()

        controller = C1Controller(this)
        
        nextLevelInvisible = NextLevelInvisible(Vector2f(2364f, 665f), player, 320, 64)
        message = ConsoleMessage(console, "Hello there... Welcome to the farm of Kebir.~" +
                "Use W, A, S, D to move around the farm.~" +
                "Great Job! Now try and find some locals to talk to.~" +
                "I should head towards the North trail and see if I can help...", null)
        message.runTo(3)
    }

    private fun handleNPC() {
        super.npcs.add(NPC(Vector2f(1686f, 3050f), "Mr. Crow", tileManager.objectsMap,
                player, console, Sprite("/entity/npc/mr-crow.png", 64, 64), 128, 128,
                "Would you look at my nice wheat. I think it's time to harvest that.").also { npcMrCrow = it })
        super.npcs.add(NPC(Vector2f(3520f, 1974f), "Amber", tileManager.objectsMap,
                player, console, Sprite("/entity/npc/amber.png", 64, 64), 128, 128,
                "I sure do love these flowers. I wish they would stay year-round.").also { npcAmber = it })
        super.npcs.add(NPC(Vector2f(2644f, 1933f), "Mrs. Caren", tileManager.objectsMap,
                player, console, Sprite("/entity/npc/mrs-caren.png", 64, 64), 128, 128,
                "Hi Deary...").also { npcMrsCaren = it })
        super.npcs.add(NPC(Vector2f(2141f, 2572f), "Matt", tileManager.objectsMap,
                player, console, Sprite("/entity/npc/matt.png", 64, 64), 128, 128,
                """
                    I barley made it out alive. It came attackn' and didn't flinch after hit with an arrow. It took three whole arrows
                    before it finally went skablat.
                    """.trimIndent()).also { matt = it })
        matt.hide = true
        npcAmber.setCurrentAnimation(NPC.ANIMATION_DOWN)
        npcMrsCaren.setCurrentAnimation(NPC.ANIMATION_LEFT)
        npcMrCrow.setCurrentAnimation(NPC.ANIMATION_DOWN)
    }

    override fun update() {
        super.update()
        if (isControlled) controller?.update()
        if (!stage1Done && npcAmber.hasTalked() && npcMrCrow.hasTalked()) stage1Done = true
        if (stage1Done && !console.isShowConsole && !displayedCutScene && !controller?.hasControlled()!! && !isControlled) {
            displayedCutScene = true
        }
        if (displayedCutScene) {
            displayedCutScene = false
            isControlled = true
            controller?.onCreate()
        }
        if (stage2Done && npcMrsCaren.hasTalked() && npcMrsCaren.message.currentMessageID >=
                npcMrsCaren.message.lines.size) {
            stage2Done = false
            nextLevelInvisible.shouldDetect = true
            message.runTo(4)
            console.isShowConsole = true
        }
        nextLevelInvisible.update()
        message.update()
    }

    override fun draw(g: Graphics2D?) {
        super.draw(g)
        if (isControlled) {
            controller?.draw(g)
        }
        nextLevelInvisible.draw(g)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isControlled) controller?.onDestroy()
    }

    fun updateCarenLines() {
        npcMrsCaren.changeLines("""Oh, Hello Deary. I guess you're wondering about that slime that attacked dear old Matty Boy. Well...~Let me tell you something. There is an evil in this world that is far beyond anything you could imagine.~A long time ago there were five crystals, called The Crystals of Everything. They were in fact crystals of 
 everything.~You see if you were holding the crystals they gave you gift.~You see one gave speed, one gave strength, anther magic, the next power of creation,
and finally the power of destruction.~These crystals were very valuable because people who had possession of the
became as powerful as gods.~These crystals were placed by the old ones in the far reachesof the world. Here no one could ever have
the power of all five crystals at once.~However, rumor has it, someone by the name of The Dark Lord 
gathered up all of the crystals and used them to spread evil throughout the world.~He then banished the crystals so their magic could never be used again.~However rumor has it that someone with pure intention can gather up all
of the crystals again and restore peace to the world.""")
        stage2Done = true
        matt.hide = false
    }

}