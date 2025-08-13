package com.spamalot.shooter.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonFiles {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T read(String path, Class<T> type) {
        try (Reader r = Files.newBufferedReader(Path.of(path))) {
            return GSON.fromJson(r, type);
        } catch (IOException e) { return null; }
    }

    public static <T> void write(String path, T obj) {
        try {
            Files.createDirectories(Path.of(path).getParent());
            try (Writer w = Files.newBufferedWriter(Path.of(path))) { GSON.toJson(obj, w); }
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public static <T> List<T> readList(String path, Type t) {
        try (Reader r = Files.newBufferedReader(Path.of(path))) {
            return GSON.fromJson(r, t);
        } catch (IOException e) { return null; }
    }

    public static <T> void writeList(String path, List<T> list) {
        write(path, list);
    }
}
