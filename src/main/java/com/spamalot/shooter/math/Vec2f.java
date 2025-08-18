package com.spamalot.shooter.math;

public record Vec2f(float x, float y) {
  public static final Vec2f ZERO = new Vec2f(0, 0);

  public Vec2f mul(float k) {
    return new Vec2f(x * k, y * k);
  }

  public Vec2f add(Vec2f o) {
    return new Vec2f(x + o.x, y + o.y);
  }

  public float len() {
    return (float) Math.sqrt(x * x + y * y);
  }

  public Vec2f norm() {
    float l = len();
    return l > 0 ? new Vec2f(x / l, y / l) : ZERO;
  }

  public Vec2f deadzone(float dz) {
    float l = len();
    return l < dz ? ZERO : new Vec2f(x, y);
  }
}
