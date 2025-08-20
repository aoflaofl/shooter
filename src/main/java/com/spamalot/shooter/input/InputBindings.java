package com.spamalot.shooter.input;

import com.google.gson.reflect.TypeToken;
import com.spamalot.shooter.io.JsonFiles;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores mappings from game actions to physical inputs.
 *
 * <p>Bindings are loaded from and saved to JSON using {@link JsonFiles}. The
 * {@link Gamepad} class queries this map to resolve actions into button or axis
 * indices.</p>
 */
public class InputBindings {
  private final Map<String, Binding> map = new HashMap<>();

  /**
   * Loads bindings from a JSON file or falls back to defaults.
   */
  public static InputBindings loadOrDefault(String path) {
    var b = new InputBindings();
    Type t = new TypeToken<List<Binding>>() {
    }.getType();
    List<Binding> list = JsonFiles.readList(path, t);
    if (list == null) {
      list = defaultBindings();
    }
    list.forEach(bind -> b.map.put(bind.action, bind));
    return b;
  }

  /** Retrieves the binding associated with the given action. */
  public Binding get(String action) {
    return map.get(action);
  }

  /** Adds or replaces a binding for the specified action. */
  public void put(Binding b) {
    map.put(b.action, b);
  }

  /** Saves the current bindings to disk. */
  public void save(String path) {
    JsonFiles.writeList(path, new ArrayList<>(map.values()));
  }

  /**
   * Generates a set of default bindings used when no configuration file exists.
   */
  private static List<Binding> defaultBindings() {
    final List<Binding> list = new ArrayList<>();

    var move = new Binding();
    move.action = InputAction.MOVE;
    move.type = InputType.AXIS2;
    move.axisX = 0;
    move.axisY = 1;
    list.add(move);

    var fire = new Binding();
    fire.action = InputAction.FIRE;
    fire.type = InputType.BUTTON;
    fire.button = 0;
    list.add(fire);

    var dash = new Binding();
    dash.action = InputAction.DASH;
    dash.type = InputType.BUTTON;
    dash.button = 1;
    list.add(dash);

    var ok = new Binding();
    ok.action = InputAction.UI_ACCEPT;
    ok.type = InputType.BUTTON;
    ok.button = 0;
    list.add(ok);

    var back = new Binding();
    back.action = InputAction.UI_BACK;
    back.type = InputType.BUTTON;
    back.button = 1;
    list.add(back);

    return list;
  }
}
