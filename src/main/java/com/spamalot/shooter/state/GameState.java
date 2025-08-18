package com.spamalot.shooter.state;

public interface GameState {
  void update(double dt);

  void render();

  void dispose();
}
