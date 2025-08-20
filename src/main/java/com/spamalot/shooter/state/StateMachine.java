package com.spamalot.shooter.state;

import java.util.ArrayDeque;

/**
 * Simple stack-based state machine for managing game screens.
 *
 * <p>The state machine maintains a stack of {@link GameState} instances. The
 * state on the top of the stack is considered active and receives update and
 * render calls. States can push new states on top (for modal dialogs, menus,
 * etc.) or request a change, which replaces the current state.</p>
 */
public class StateMachine {
  /** Stack of active states, top element is the current state. */
  private final ArrayDeque<GameState> stack = new ArrayDeque<>();

  /**
   * Pushes a new state on top of the stack, making it the active state.
   */
  public void push(GameState s) {
    stack.push(s);
  }

  /**
   * Removes the current state from the stack and disposes of it.
   */
  public void pop() {
    var s = stack.pop();
    s.dispose();
  }

  /**
   * Replaces the current state with a new one.
   *
   * <p>If a state is already active it is popped and disposed before the new
   * state is pushed.</p>
   */
  public void change(GameState s) {
    if (!stack.isEmpty()) {
      pop();
    }
    push(s);
  }

  /**
   * Updates the current active state.
   *
   * @param dt time in seconds since the last update
   */
  public void update(double dt) {
    if (!stack.isEmpty()) {
      stack.peek().update(dt);
    }
  }

  /**
   * Renders the current active state.
   */
  public void render() {
    if (!stack.isEmpty()) {
      stack.peek().render();
    }
  }

  /**
   * Disposes all states on the stack.
   */
  public void dispose() {
    while (!stack.isEmpty()) {
      pop();
    }
  }
}
