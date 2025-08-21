package com.spamalot.shooter.state;

import com.spamalot.shooter.audio.Sound;
import com.spamalot.shooter.ecs.Bullet;
import com.spamalot.shooter.ecs.Player;
import com.spamalot.shooter.input.Gamepad;
import com.spamalot.shooter.math.Vec2f;
import com.spamalot.shooter.render.Renderer2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
  private final List<Bullet> bullets = new ArrayList<>();
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;

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
    if (Gamepad.actionJustPressed("FIRE")) {
      bullets.add(new Bullet(player.pos(), new Vec2f(0, -700)));
      if (!shootSound.isPlaying()) {
        shootSound.play();
      }
    }

    Iterator<Bullet> it = bullets.iterator();
    while (it.hasNext()) {
      Bullet b = it.next();
      b.update(dt);
      Vec2f p = b.pos();
      if (p.x() < 0 || p.x() > WIDTH || p.y() < 0 || p.y() > HEIGHT) {
        it.remove();
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
    for (Bullet b : bullets) {
      b.draw(renderer);
    }
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
