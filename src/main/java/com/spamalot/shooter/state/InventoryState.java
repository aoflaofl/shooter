package com.spamalot.shooter.state;

/**
 * Placeholder state for an inventory screen.
 *
 * <p>The implementation is currently empty but demonstrates how additional
 * states can be plugged into the {@link StateMachine}. In a full game this
 * state would handle item management and related UI interactions.</p>
 */
public class InventoryState implements GameState {
  @Override
  public void update(double dt) {
    // No logic yet
  }

  @Override
  public void render() {
    // Nothing to draw yet
  }

  @Override
  public void dispose() {
    // No resources to free
  }
}
