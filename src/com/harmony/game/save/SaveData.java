package com.harmony.game.save;

import java.io.*;

public class SaveData implements Serializable {

    public static final String SAVE_LOCATION = "res/_save_data/player.data";
    private static final File file = new File(SAVE_LOCATION);

    public final int currentLevel;
    public final PlayerSave playerSave;

    public SaveData(int currentLevel, PlayerSave playerSave) {
        this.currentLevel = currentLevel;
        this.playerSave = playerSave;
    }

    public void save() {
        try {
            if(!file.exists()) file.createNewFile();

            FileOutputStream file = new FileOutputStream(SAVE_LOCATION);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(this);

            out.close();
            file.close();

            System.out.println("*** SAVE DATA HAS BEEN SERIALIZED ***");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SaveData loadSaveData() {
        if(!file.exists()) return null;

        try {
            FileInputStream file = new FileInputStream(SAVE_LOCATION);
            ObjectInputStream in = new ObjectInputStream(file);

            SaveData data = (SaveData) in.readObject();

            in.close();
            file.close();

            System.out.println("*** SAVE DATA HAS BEEN DESERIALIZED ***");

            return data;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
