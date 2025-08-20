package com.spamalot.shooter.state;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import com.spamalot.shooter.input.Gamepad;
import com.spamalot.shooter.render.Renderer2D;

/**
 * Game state representing the main menu screen.
 *
 * <p>The menu waits for player input using the {@link Gamepad} helper and
 * transitions to other states based on the received actions. Rendering is
 * handled via a {@link Renderer2D} instance.</p>
 */
public class MenuState implements GameState {
  /** Handle to the GLFW window so the state can request closing it. */
  private final long window;
  /** Reference to the global state machine for transitions. */
  private final StateMachine sm;
  /** Renderer used to draw menu text. */
  private final Renderer2D renderer = new Renderer2D();

  /**
   * Constructs a new menu state.
   *
   * @param window GLFW window handle
   * @param sm     state machine used for state transitions
   */
  public MenuState(long window, StateMachine sm) {
    this.window = window;
    this.sm = sm;
  }

  /**
   * Polls input and reacts to menu actions.
   *
   * <p>Pressing the accept action pushes the gameplay state, while pressing the
   * back action sets the GLFW window to close.</p>
   */
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

  /**
   * Renders the menu UI using simple bitmap text.
   */
  @Override
  public void render() {
    renderer.begin();
    renderer.text(40, 60, "Shooter Demo");
    renderer.text(40, 120, "Press A to Start");
    renderer.end();
  }

  /** Releases renderer resources when the state is disposed. */
  @Override
  public void dispose() {
    renderer.dispose();
  }
}
