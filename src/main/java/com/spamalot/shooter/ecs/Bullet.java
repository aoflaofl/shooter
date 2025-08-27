package com.spamalot.shooter.ecs;

import com.spamalot.shooter.math.Vec2f;
import com.spamalot.shooter.render.Renderer2D;
import com.spamalot.shooter.render.Texture;

/**
 * A simple projectile fired by the player.
 */
public class Bullet {
  private Vec2f pos;
  private final Vec2f vel;
  private static final float WIDTH = 8;
  private static final float HEIGHT = 8;
  private static final Texture TEX = Texture.load("assets/textures/bullet.png");

  /**
   * Creates a bullet at the given position travelling at the given velocity.
   *
   * @param pos starting position
   * @param vel velocity vector
   */
  public Bullet(Vec2f pos, Vec2f vel) {
    this.pos = pos;
    this.vel = vel;
  }

  /** Update the bullet position. */
  public void update(double dt) {
    pos = pos.add(vel.mul((float) dt));
  }

  /**
   * Draw the bullet at the specified screen coordinates.
   *
   * @param r renderer used for drawing
   * @param x screen x coordinate of the bullet center
   * @param y screen y coordinate of the bullet center
   */
  public void draw(Renderer2D r, float x, float y) {
    r.sprite(TEX, x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
  }

  /** Current position of the bullet. */
  public Vec2f pos() {
    return pos;
  }
}
