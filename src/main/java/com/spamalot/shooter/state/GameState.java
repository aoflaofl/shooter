package com.spamalot.shooter.state;

/**
 * Represents a distinct mode or screen in the game.
 *
 * <p>Implementations of this interface encapsulate their own update and render
 * logic. The {@link StateMachine} invokes these methods on the currently active
 * state.</p>
 */
public interface GameState {
  /**
   * Updates the state's logic.
   *
   * @param dt time in seconds since the last update
   */
  void update(double dt);

  /**
   * Renders the state's visuals to the screen.
   */
  void render();

  /**
   * Releases any resources held by the state when it is removed from the stack.
   */
  void dispose();
}
