package com.spamalot.shooter.render;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.opengl.GL;

/**
 * Utility class for initializing and configuring the OpenGL context.
 *
 * <p>All methods in this class wrap LWJGL calls that set up the rendering
 * environment for the game. The methods are static because OpenGL state is
 * global to the current thread and does not require an instance of this class.</p>
 */
public final class OpenGlInitializer {
  /** Utility class - prevent instantiation. */
  private OpenGlInitializer() {
  }

  /**
   * Creates the OpenGL capabilities for the current context.
   *
   * <p>{@link GL#createCapabilities()} queries the underlying driver for all
   * supported OpenGL functions and makes them available through LWJGL. This must
   * be called once after a context is made current before using any OpenGL
   * functions.</p>
   */
  public static void create() {
    GL.createCapabilities();
  }

  /**
   * Sets up a 2D orthographic projection for rendering.
   *
   * <p>The sequence of {@code glMatrixMode}, {@code glLoadIdentity} and
   * {@code glOrtho} configures the projection matrix so that OpenGL coordinates
   * map directly to screen pixels, with the origin in the top-left corner. After
   * modifying the projection matrix, the model-view matrix is reset to the
   * identity to prepare for drawing.</p>
   *
   * @param w the width of the viewport in pixels
   * @param h the height of the viewport in pixels
   */
  public static void ortho(int w, int h) {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, w, h, 0, -1, 1);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
  }
}
