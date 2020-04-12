package com.harmony.game.state;

import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Font;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.save.PlayerSave;
import com.harmony.game.save.SaveData;
import com.harmony.game.utils.ImageUtils;
import com.harmony.game.utils.Input;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerState extends State {

    private Sprite buttons;

    private final Rectangle selectionOne = new Rectangle(200, 200, 300, 400);
    private final Rectangle selectionTwo = new Rectangle(Display.width - 500, 200, 300, 400);

    private final Rectangle keepButton = new Rectangle(Display.width / 2 - 300, 550, 625, 75);
    private final Rectangle discardButton = new Rectangle(Display.width / 2 - 300, 635, 625, 75);

    private int keepButtonAnimation = 0;
    private int discardButtonAnimation = 0;

    private BufferedImage maleLight;
    private BufferedImage femaleLight;

    private BufferedImage toneOne;
    private BufferedImage toneTwo;

    private BufferedImage selectedPlayer;

    private PlayerSave.Gender gender;
    private PlayerSave.SkinTone skinTone;

    private int stage = 1;

    @Override
    public void onCreate() {
        buttons = new Sprite("/ui/buttons_formatted.png", 120, 26);

        maleLight = ImageUtils.loadImage("/entity/player/male_light.png").getSubimage(64 * 4, 64 * 2, 64, 64);
        femaleLight = ImageUtils.loadImage("/entity/player/female_light.png").getSubimage(64 * 4, 64 * 2, 64, 64);
    }

    @Override
    public void update() {
        if(stage < 3) {
            if (Input.hoverRectangle(selectionOne) || Input.hoverRectangle(selectionTwo))
                Display.setCursor(Cursor.HAND_CURSOR);
            else Display.setCursor(Cursor.DEFAULT_CURSOR);
        }

        if (stage == 1) {
            if(Input.hoverRectangle(selectionOne) && Input.isButtonUp(1)) {
                gender = PlayerSave.Gender.FEMALE;
                toneOne = femaleLight;
                toneTwo = ImageUtils.loadImage("/entity/player/female_dark.png").getSubimage(64 * 4, 64 * 2, 64, 64);
            }
            else if(Input.hoverRectangle(selectionTwo) && Input.isButtonUp(1)) {
                gender = PlayerSave.Gender.MALE;
                toneOne = maleLight;
                toneTwo = ImageUtils.loadImage("/entity/player/male_dark.png").getSubimage(64 * 4, 64 * 2, 64, 64);
            } else return;

            maleLight.flush();
            femaleLight.flush();

            stage = 2;
            return;
        }

        if(stage == 2) {
            if(Input.hoverRectangle(selectionOne) && Input.isButtonUp(1)) {
                skinTone = PlayerSave.SkinTone.LIGHT;
                selectedPlayer = toneOne;
            }
            else if(Input.hoverRectangle(selectionTwo) && Input.isButtonUp(1)) {
                skinTone = PlayerSave.SkinTone.DARK;
                selectedPlayer = toneTwo;
            } else return;

            toneOne.flush();
            toneTwo.flush();

            stage = 3;
            return;
        }

        if(stage == 3) {
            if(Input.hoverRectangle(keepButton) || Input.hoverRectangle(discardButton)) Display.setCursor(Cursor.HAND_CURSOR);
            else Display.setCursor(Cursor.DEFAULT_CURSOR);

            if(Input.hoverRectangle(keepButton) && Input.isButtonUp(1)) {
                MenuState.saveData = new SaveData(0, new PlayerSave(gender, skinTone));
                MenuState.saveData.save();
                GameStateManager.setCurrentState(MenuState.saveData.currentLevel);
            } else if(Input.hoverRectangle(discardButton) && Input.isButtonUp(1)) {
                GameStateManager.setCurrentState(GameStateManager.PLAYER_STATE);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        // Background
        g.setColor(new Color(33, 30, 39));
        g.fillRect(0, 0, Display.width, Display.height);

        // Selection Boxes
        if(stage < 3) {
            g.setColor(new Color(79, 71, 95));
            g.fillRect(selectionOne.x, selectionOne.y, selectionOne.width, selectionOne.height);
            g.fillRect(selectionTwo.x, selectionTwo.y, selectionTwo.width, selectionTwo.height);
        }

        if(stage == 1) {
            // Header Text
            Font.TRANSPARENT_FONT.drawText(g, "Choose Gender", Display.width / 2 - 277, 10, 64);

            // Selection Images
            g.drawImage(femaleLight, selectionOne.x + selectionOne.width / 2 - 128,
                    selectionOne.y + selectionOne.height / 2 - 128, 256, 256, null);
            g.drawImage(maleLight, selectionTwo.x + selectionTwo.width / 2 - 128,
                    selectionTwo.y + selectionTwo.height / 2 - 128, 256, 256, null);
        } else if(stage == 2) {
            // Header Text
            Font.TRANSPARENT_FONT.drawText(g, "Choose Skin Tone", Display.width / 2 - 341, 10, 64);

            // Selection Images
            g.drawImage(toneOne, selectionOne.x + selectionOne.width / 2 - 128,
                    selectionOne.y + selectionOne.height / 2 - 128, 256, 256, null);
            g.drawImage(toneTwo, selectionTwo.x + selectionTwo.width / 2 - 128,
                    selectionTwo.y + selectionTwo.height / 2 - 128, 256, 256, null);
        } else if(stage == 3) {
            // Header Text
            Font.TRANSPARENT_FONT.drawText(g, "Your Player", Display.width / 2 - 235, 10, 64);

            // Selected Player Image
            g.drawImage(selectedPlayer, Display.width / 2 - 128, Display.height / 2 - 128, 256, 256, null);

            // Draw Buttons
            g.drawImage(buttons.getSprite(keepButtonAnimation, 0), keepButton.x, keepButton.y,
                    keepButton.width, keepButton.height, null);
            g.drawImage(buttons.getSprite(discardButtonAnimation, 0), discardButton.x, discardButton.y,
                    discardButton.width, discardButton.height, null);

            Font.TRANSPARENT_FONT.drawText(g, "Keep", keepButton.x + keepButton.width / 2 - 86,
                    keepButton.y + keepButton.height / 2 - 32, 64);
            Font.TRANSPARENT_FONT.drawText(g, "Discard", discardButton.x + discardButton.width / 2 - 149,
                    discardButton.y + discardButton.height / 2 - 32, 64);
        }
    }
}
