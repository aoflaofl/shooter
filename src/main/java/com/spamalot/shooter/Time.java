package com.spamalot.shooter;

/**
 * Utility class for managing frame timing in the game loop.
 * 
 * <p>Tracks elapsed time between frames and provides delta time for smooth
 * updates.</p>
 */
public class Time {
  /** The timestamp of the previous frame. */
  private double lastTime = glfwTime();
  /** The time difference between the current and previous frame. */
  private double delta;

  /**
   * Updates the timing information for the current frame.
   * 
   * <p>Calculates the delta time and updates the last frame timestamp.</p>
   */
  public void tick() {
    double now = glfwTime();
    delta = Math.min(1.0 / 30.0, now - lastTime);
    lastTime = now;
  }

  /**
   * Returns the time difference between the current and previous frame.
   *
   * @return delta time in seconds
   */
  public double delta() {
    return delta;
  }

  /**
   * Retrieves the current time from GLFW.
   *
   * @return current time in seconds
   */
  private static double glfwTime() {
    return org.lwjgl.glfw.GLFW.glfwGetTime();
  }
}
