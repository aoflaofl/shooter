package com.spamalot.shooter.state;

import com.spamalot.shooter.audio.Sound;
import com.spamalot.shooter.ecs.Player;
import com.spamalot.shooter.input.Gamepad;
import com.spamalot.shooter.render.Renderer2D;

public class GameplayState implements GameState {
  private final Renderer2D renderer = new Renderer2D();
  private final Player player = new Player();
  private final Sound shootSound = Sound.load("/assets/sounds/shoot.wav");

  public GameplayState(long window, StateMachine sm) {
  }

  @Override
  public void update(double dt) {
    Gamepad.poll();
    var move = Gamepad.axis("MOVE");
    player.move(move, dt);
    if (Gamepad.actionPressed("FIRE")) {
      if (!shootSound.isPlaying()) {
        shootSound.play();
      }
    }
  }

  @Override
  public void render() {
    renderer.begin();
    player.draw(renderer);
    renderer.end();
  }

  @Override
  public void dispose() {
    renderer.dispose();
    shootSound.dispose();
  }
}
