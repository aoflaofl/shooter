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

public final class Gamepad {
  private static final int PAD_INDEX = GLFW_JOYSTICK_1;
  private static final GLFWGamepadState STATE = GLFWGamepadState.create();
  private static final GLFWGamepadState PREV = GLFWGamepadState.create();
  private static InputBindings bindings;

  public static void init() {
    ByteBuffer mappings = MemoryUtil.memUTF8("");
    glfwUpdateGamepadMappings(mappings);
    MemoryUtil.memFree(mappings);
    bindings = InputBindings.loadOrDefault("config/bindings.json");
  }

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

  public static boolean actionJustPressed(String action) {
    var b = bindings.get(action);
    if (b == null || b.type != InputType.BUTTON) {
      return false;
    }
    return PREV.buttons(b.button) == GLFW_RELEASE && STATE.buttons(b.button) == GLFW_PRESS;
  }

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

  public static void rebind(String action, Binding newBinding) {
    bindings.put(newBinding);
    bindings.save("config/bindings.json");
  }

  private Gamepad() {
  }
}
