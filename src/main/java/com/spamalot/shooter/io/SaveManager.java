package com.spamalot.shooter.io;

public class SaveManager {
    public static SaveData load(String slot) {
        var data = JsonFiles.read("saves/" + slot + ".json", SaveData.class);
        return data == null ? new SaveData() : data;
    }
    public static void save(String slot, SaveData data) {
        JsonFiles.write("saves/" + slot + ".json", data);
    }
}
