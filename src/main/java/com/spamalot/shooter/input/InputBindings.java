package com.spamalot.shooter.input;

import com.google.gson.reflect.TypeToken;
import com.spamalot.shooter.io.JsonFiles;

import java.lang.reflect.Type;
import java.util.*;

public class InputBindings {
    private final Map<String, Binding> map = new HashMap<>();

    public static InputBindings loadOrDefault(String path) {
        var b = new InputBindings();
        Type t = new TypeToken<List<Binding>>(){}.getType();
        List<Binding> list = JsonFiles.readList(path, t);
        if (list == null) list = defaultBindings();
        list.forEach(bind -> b.map.put(bind.action, bind));
        return b;
    }

    public Binding get(String action) { return map.get(action); }
    public void put(Binding b) { map.put(b.action, b); }
    public void save(String path) { JsonFiles.writeList(path, new ArrayList<>(map.values())); }

    private static List<Binding> defaultBindings() {
        List<Binding> list = new ArrayList<>();

        var move = new Binding();
        move.action = InputAction.MOVE; move.type = InputType.AXIS2; move.axisX = 0; move.axisY = 1; list.add(move);

        var fire = new Binding();
        fire.action = InputAction.FIRE; fire.type = InputType.BUTTON; fire.button = 0; list.add(fire);

        var dash = new Binding();
        dash.action = InputAction.DASH; dash.type = InputType.BUTTON; dash.button = 1; list.add(dash);

        var ok = new Binding();
        ok.action = InputAction.UI_ACCEPT; ok.type = InputType.BUTTON; ok.button = 0; list.add(ok);

        var back = new Binding();
        back.action = InputAction.UI_BACK; back.type = InputType.BUTTON; back.button = 1; list.add(back);

        return list;
    }
}
