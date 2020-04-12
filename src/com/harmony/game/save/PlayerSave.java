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

    public PlayerSave(Gender gender, SkinTone skinTone) {
        this.gender = gender;
        this.skinTone = skinTone;
    }

    public BufferedImage getPlayerImage() {
        return ImageUtils.loadImage( "/entity/player/" + gender.name().toLowerCase() + "_" +
                skinTone.name().toLowerCase() + ".png");
    }

}
