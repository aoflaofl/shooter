package com.spamalot.shooter.math;

/**
 * Minimal 2D float vector utility record.
 */
public record Vec2f(float x, float y) {
  /** Zero vector constant. */
  public static final Vec2f ZERO = new Vec2f(0, 0);

  /** @return a new vector scaled by {@code k}. */
  public Vec2f mul(float k) {
    return new Vec2f(x * k, y * k);
  }

  /** @return the component-wise addition of this and {@code o}. */
  public Vec2f add(Vec2f o) {
    return new Vec2f(x + o.x, y + o.y);
  }

  /** @return the Euclidean length of the vector. */
  public float len() {
    return (float) Math.sqrt(x * x + y * y);
  }

  /** @return a normalized copy of the vector. */
  public Vec2f norm() {
    float l = len();
    return l > 0 ? new Vec2f(x / l, y / l) : ZERO;
  }

  /**
   * Applies a deadzone filter to the vector.
   *
   * @param dz radius of the deadzone
   * @return zero if the vector's length is below the deadzone, otherwise the
   *         original vector
   */
  public Vec2f deadzone(float dz) {
    float l = len();
    return l < dz ? ZERO : new Vec2f(x, y);
  }
}
