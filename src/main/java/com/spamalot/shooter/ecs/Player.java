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
  private final float speed = 350f;
  /** Current velocity of the player. */
  private Vec2f vel = Vec2f.ZERO;
  /** Last non-zero facing direction. */
  private Vec2f facing = new Vec2f(0, -1);
  /** Pre-rendered textures for 8 facing directions. */
  private final Texture[] texDirs = {
      Texture.load("assets/textures/player_up.png"),
      Texture.load("assets/textures/player_up_right.png"),
      Texture.load("assets/textures/player_right.png"),
      Texture.load("assets/textures/player_down_right.png"),
      Texture.load("assets/textures/player_down.png"),
      Texture.load("assets/textures/player_down_left.png"),
      Texture.load("assets/textures/player_left.png"),
      Texture.load("assets/textures/player_up_left.png")
  };
  private static final float WIDTH = 48;
  private static final float HEIGHT = 48;

  /**
   * Moves the player based on a directional axis input.
   *
   * @param axis directional input vector
   * @param dt   delta time in seconds
   */
  public void move(Vec2f axis, double dt) {
    vel = axis.norm().mul(speed);
    if (vel.len() > 0) {
      facing = vel.norm();
    }
    pos = pos.add(vel.mul((float) dt));
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
    int idx = dirIndex(facing);
    r.sprite(texDirs[idx], x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
  }

  private int dirIndex(Vec2f d) {
    double angle = Math.atan2(d.y(), d.x()) + Math.PI / 2.0;
    if (angle < 0) {
      angle += Math.PI * 2.0;
    }
    double sector = Math.PI / 4.0; // 8 directions
    return (int) Math.round(angle / sector) % 8;
  }

  /**
   * Current center position of the player.
   *
   * @return player position
   */
  public Vec2f pos() {
    return pos;
  }

  /** Current facing direction as a unit vector. */
  public Vec2f facing() {
    return facing;
  }
}
