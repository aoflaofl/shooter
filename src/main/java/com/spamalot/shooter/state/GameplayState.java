package com.spamalot.shooter.state;

import com.spamalot.shooter.audio.Sound;
import com.spamalot.shooter.ecs.Player;
import com.spamalot.shooter.input.Gamepad;
import com.spamalot.shooter.render.Renderer2D;

/**
 * Core gameplay state where the player controls the character.
 *
 * <p>Input is read through the {@link Gamepad} class and translated into
 * movement or firing actions. Rendering is performed using a simple
 * {@link Renderer2D}. A shooting sound effect is played when the fire action is
 * held.</p>
 */
public class GameplayState implements GameState {
  private final Renderer2D renderer = new Renderer2D();
  private final Player player = new Player();
  private final Sound shootSound = Sound.load("/assets/sounds/shoot.wav");

  /**
   * Constructs the gameplay state. Parameters are currently unused but mirror
   * those of other states for future expansion.
   */
  public GameplayState(long window, StateMachine sm) {
  }

  /**
   * Updates game logic such as player movement and shooting.
   *
   * <p>Polling the {@link Gamepad} yields axis values for movement and button
   * states for firing. The state machine itself is not modified here but the
   * player entity reacts to the input.</p>
   */
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

  /**
   * Renders the player sprite to the screen.
   */
  @Override
  public void render() {
    renderer.begin();
    player.draw(renderer);
    renderer.end();
  }

  /**
   * Frees OpenGL and audio resources.
   */
  @Override
  public void dispose() {
    renderer.dispose();
    shootSound.dispose();
  }
}
