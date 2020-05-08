package com.harmony.game.save;

import com.harmony.game.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class PlayerSave implements Serializable {

    public enum Gender {
        MALE, FEMALE
    }

    public enum SkinTone {
        DARK, LIGHT
    }

    public final Gender gender;
    public final SkinTone skinTone;

    public final int health;
    public final int xp;
    public final int magicPoints;
    public final int currency;

    public PlayerSave(Gender gender, SkinTone skinTone, int health, int xp, int magicPoints, int currency) {
        this.gender = gender;
        this.skinTone = skinTone;

        this.health = health;
        this.xp = xp;
        this.magicPoints = magicPoints;
        this.currency = currency;
    }

    public BufferedImage getPlayerImage() {
        return ImageUtils.loadImage( "/entity/player/" + gender.name().toLowerCase() + "_" +
                skinTone.name().toLowerCase() + ".png");
    }

}
