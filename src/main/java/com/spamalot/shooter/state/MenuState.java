package com.spamalot.shooter.state;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import com.spamalot.shooter.input.Gamepad;
import com.spamalot.shooter.render.Renderer2D;

public class MenuState implements GameState {
  private final long window;
  private final StateMachine sm;
  private final Renderer2D renderer = new Renderer2D();

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
    renderer.begin();
    renderer.text(40, 60, "Shooter Demo");
    renderer.text(40, 120, "Press A to Start");
    renderer.end();
  }

  @Override
  public void dispose() {
    renderer.dispose();
  }
}
