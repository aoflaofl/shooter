package com.spamalot.shooter.input;

import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetGamepadState;
import static org.lwjgl.glfw.GLFW.glfwJoystickIsGamepad;
import static org.lwjgl.glfw.GLFW.glfwUpdateGamepadMappings;

import com.spamalot.shooter.math.Vec2f;
import java.nio.ByteBuffer;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.system.MemoryUtil;

/**
 * Handles gamepad input for the shooter game.
 * 
 * <p>This class manages polling, state tracking, and input bindings for a
 * gamepad device using LWJGL's GLFW integration. It provides methods to
 * initialize the gamepad, poll its state, query button and axis actions, and
 * rebind input actions.</p>
 */
public final class Gamepad {
  /** The index of the gamepad to use (GLFW_JOYSTICK_1). */
  private static final int PAD_INDEX = GLFW_JOYSTICK_1;
  /** Current state of the gamepad. */
  private static final GLFWGamepadState STATE = GLFWGamepadState.create();
  /** Previous state of the gamepad. */
  private static final GLFWGamepadState PREV = GLFWGamepadState.create();
  /** Input bindings configuration. */
  private static InputBindings bindings;

  /**
   * Initializes the gamepad input system.
   * 
   * <p>Updates gamepad mappings and loads input bindings from configuration.</p>
   */
  public static void init() {
    ByteBuffer mappings = MemoryUtil.memUTF8("");
    glfwUpdateGamepadMappings(mappings);
    MemoryUtil.memFree(mappings);
    bindings = InputBindings.loadOrDefault("config/bindings.json");
  }

  /**
   * Polls the current state of the gamepad.
   * 
   * <p>Updates the previous state, retrieves the current state if a gamepad is
   * connected, or resets the state if not.</p>
   */
  public static void poll() {
    PREV.set(STATE);
    if (glfwJoystickIsGamepad(PAD_INDEX)) {
      glfwGetGamepadState(PAD_INDEX, STATE);
    } else {
      for (int i = 0; i < STATE.buttons().remaining(); i++) {
        STATE.buttons().put(i, (byte) 0);
      }
      for (int i = 0; i < STATE.axes().remaining(); i++) {
        STATE.axes().put(i, 0f);
      }
    }
  }

  /**
   * Checks if the specified action is currently pressed.
   *
   * @param action The name of the action to check.
   * @return {@code true} if the action is pressed, {@code false} otherwise.
   */
  public static boolean actionPressed(String action) {
    var b = bindings.get(action);
    if (b == null) {
      return false;
    }
    if (b.type == InputType.BUTTON) {
      return STATE.buttons(b.button) == GLFW_PRESS;
    }
    return false;
  }

  /**
   * Checks if the specified action was just pressed (transition from released to
   * pressed).
   *
   * @param action The name of the action to check.
   * @return {@code true} if the action was just pressed, {@code false} otherwise.
   */
  public static boolean actionJustPressed(String action) {
    var b = bindings.get(action);
    if (b == null || b.type != InputType.BUTTON) {
      return false;
    }
    System.out.println("Action: " + b.action + " - Button: " + b.button);
    return PREV.buttons(b.button) == GLFW_RELEASE && STATE.buttons(b.button) == GLFW_PRESS;
  }

  /**
   * Gets the axis value(s) for the specified action.
   *
   * @param action The name of the action to query.
   * @return A {@link Vec2f} representing the axis value(s), or {@link Vec2f#ZERO}
   *         if not applicable.
   */
  public static Vec2f axis(String action) {
    var b = bindings.get(action);
    if (b == null) {
      return Vec2f.ZERO;
    }
    if (b.type == InputType.AXIS2) {
      float x = STATE.axes(b.axisX);
      float y = STATE.axes(b.axisY);
      return new Vec2f(x, y).deadzone(b.deadzone);
    }
    if (b.type == InputType.AXIS1) {
      float x = STATE.axes(b.axisX);
      return new Vec2f(x, 0);
    }
    return Vec2f.ZERO;
  }

  /**
   * Rebinds a game action to a new input binding and saves the updated bindings
   * configuration.
   * 
   * <p>This method updates the input bindings for a specified action with a new
   * binding, and then saves the updated configuration to a file.</p>
   *
   * @param action     The name of the action to be rebound. This should
   *                   correspond to an existing action in the game's input
   *                   system.
   * @param newBinding The new input binding to associate with the specified
   *                   action. This object should contain the necessary
   *                   information to define the new input mapping.
   */
  public static void rebind(String action, Binding newBinding) {
    bindings.put(newBinding);
    bindings.save("config/bindings.json");
  }

  /**
   * Private constructor to prevent instantiation.
   */
  private Gamepad() {
  }
}
