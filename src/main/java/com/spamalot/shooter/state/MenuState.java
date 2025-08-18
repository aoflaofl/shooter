package com.spamalot.shooter.state;

import static org.lwjgl.glfw.GLFW.*;

import com.spamalot.shooter.input.Gamepad;
import com.spamalot.shooter.render.Renderer2D;

public class MenuState implements GameState {
  private final long window;
  private final StateMachine sm;
  private final Renderer2D r = new Renderer2D();

  public MenuState(long window, StateMachine sm) {
    this.window = window;
    this.sm = sm;
  }

  @Override
  public void update(double dt) {
    Gamepad.poll();
    if (Gamepad.actionJustPressed("UI_ACCEPT")) {
      sm.change(new GameplayState(window, sm));
    }
    if (Gamepad.actionJustPressed("UI_BACK")) {
      glfwSetWindowShouldClose(window, true);
    }
  }

  @Override
  public void render() {
    r.begin();
    r.text(40, 60, "Shooter Demo");
    r.text(40, 120, "Press A to Start");
    r.end();
  }

  @Override
  public void dispose() {
    r.dispose();
  }
}
