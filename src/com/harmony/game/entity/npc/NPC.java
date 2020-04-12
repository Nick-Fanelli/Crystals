package com.harmony.game.entity.npc;

import com.harmony.game.entity.Entity;
import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Console;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.PlayerHelp;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class NPC extends Entity {

    protected String[] lines;
    protected int currentLine;

    protected BoxCollider talkCollider;

    protected Player player;
    protected Console console;

    /**
     * @param lines separate by new line.
     */
    public NPC(Vector2f position, ObjectTileMap objectTileMap, Player player, Console console, int width, int height, String lines) {
        super(position, objectTileMap, width, height);

        this.player = player;
        this.console = console;

        this.lines = lines.split("~");
        this.currentLine = 0;
    }

    private boolean words = false;
    private int i = 0;

    private void launchWords() {
        if(console.isWaiting()) return;

        if(i >= lines.length) {
            console.setShowConsole(false);
            words = false;
            return;
        }

        if(i <= 0) console.setShowConsole(true);

        console.sendMessage(lines[i]);
        i++;
    }

    @Override
    public void update() {
        if(talkCollider.collisionPlayer(player) && Input.isKeyDown(KeyEvent.VK_T) && !words) {
            words = true;
            i = 0;
        }

        if(words) {
            launchWords();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if(talkCollider.collisionPlayer(player) && !words) {
            PlayerHelp.showLetter(g, (int) player.position.x - 20, (int) player.position.y - 20, PlayerHelp.T_ANIMATION);
        }
    }
}
