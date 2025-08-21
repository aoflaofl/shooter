package com.spamalot.shooter.ecs;

import com.spamalot.shooter.math.Vec2f;
import com.spamalot.shooter.render.Renderer2D;
import com.spamalot.shooter.render.Texture;

/**
 * Simple player entity used in the gameplay state.
 */
public class Player {
  private Vec2f pos = new Vec2f(640, 360);
  private float speed = 350f;
  private final Texture tex = Texture.load("assets/textures/player.png");
  private static final float WIDTH = 48;
  private static final float HEIGHT = 48;

  /**
   * Moves the player based on a directional axis input.
   *
   * @param axis directional input vector
   * @param dt   delta time in seconds
   */
  public void move(Vec2f axis, double dt) {
    System.out.println(axis);
    Vec2f v = axis.norm().mul(speed * (float) dt);
    pos = pos.add(v);
  }

  /**
   * Draws the player's sprite using the provided renderer.
   */
  public void draw(Renderer2D r) {
    r.sprite(tex, pos.x() - WIDTH / 2, pos.y() - HEIGHT / 2, WIDTH, HEIGHT);
  }

  /**
   * Current center position of the player.
   *
   * @return player position
   */
  public Vec2f pos() {
    return pos;
  }
}
