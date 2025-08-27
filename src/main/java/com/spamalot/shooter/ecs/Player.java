package com.spamalot.shooter.ecs;

import com.spamalot.shooter.math.Vec2f;
import com.spamalot.shooter.render.Renderer2D;
import com.spamalot.shooter.render.Texture;

/**
 * Simple player entity used in the gameplay state.
 */
public class Player {
  /** Player position within the world. */
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
   * Wrap the player's position around the world edges.
   *
   * @param worldWidth  width of the world
   * @param worldHeight height of the world
   */
  public void wrap(float worldWidth, float worldHeight) {
    float x = pos.x();
    float y = pos.y();
    x = (x % worldWidth + worldWidth) % worldWidth;
    y = (y % worldHeight + worldHeight) % worldHeight;
    pos = new Vec2f(x, y);
  }

  /**
   * Draws the player's sprite using the provided renderer.
   */
  public void draw(Renderer2D r, float x, float y) {
    r.sprite(tex, x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
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
