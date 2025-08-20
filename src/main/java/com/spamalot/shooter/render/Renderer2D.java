package com.spamalot.shooter.render;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * Convenience wrapper around a very small immediate-mode renderer.
 *
 * <p>The class configures OpenGL for 2D rendering using LWJGL bindings and
 * exposes helper methods for drawing sprites, colored rectangles and bitmap
 * text. It intentionally uses the fixed-function pipeline (via calls such as
 * {@code glBegin}) to keep the example simple.</p>
 */
public class Renderer2D {
  /** Logical width of the rendering surface. */
  private static final int WIDTH = 1280;
  /** Logical height of the rendering surface. */
  private static final int HEIGHT = 720;
  /** Bitmap font used for the {@link #text(float, float, String)} method. */
  private BitmapFont font;

  /**
   * Creates a new renderer and configures basic OpenGL state.
   *
   * <p>The constructor sets an orthographic projection, disables depth testing
   * for 2D rendering and enables texture mapping and alpha blending so sprites
   * with transparency are composited correctly.</p>
   */
  public Renderer2D() {
    OpenGlInitializer.create();
    OpenGlInitializer.ortho(WIDTH, HEIGHT);
    glDisable(GL_DEPTH_TEST); // 2D only, no depth buffer needed
    glEnable(GL_TEXTURE_2D); // allow textured primitives
    glEnable(GL_BLEND); // enable alpha blending
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // standard alpha blend

    // Load the bitmap font used for text rendering
    font = new BitmapFont("assets/textures/font.png");
  }

  /**
   * Resets the model-view matrix before drawing a new frame.
   */
  public void begin() {
    glLoadIdentity();
  }

  /**
   * Draws a textured quad to the screen.
   *
   * <p>The texture is bound and then a quad is emitted using the immediate-mode
   * calls {@code glBegin} and {@code glVertex2f}. Texture coordinates are set up
   * so that the entire texture is mapped onto the quad.</p>
   */
  public void sprite(Texture tex, float x, float y, float w, float h) {
    tex.bind();
    glBegin(GL_QUADS);
    glTexCoord2f(0, 0);
    glVertex2f(x, y);
    glTexCoord2f(1, 0);
    glVertex2f(x + w, y);
    glTexCoord2f(1, 1);
    glVertex2f(x + w, y + h);
    glTexCoord2f(0, 1);
    glVertex2f(x, y + h);
    glEnd();
  }

  /**
   * Draws a solid colored rectangle.
   *
   * <p>Texturing is temporarily disabled so the quad is filled using the
   * current color state.</p>
   */
  public void rect(float x, float y, float w, float h) {
    glDisable(GL_TEXTURE_2D);
    glBegin(GL_QUADS);
    glVertex2f(x, y);
    glVertex2f(x + w, y);
    glVertex2f(x + w, y + h);
    glVertex2f(x, y + h);
    glEnd();
    glEnable(GL_TEXTURE_2D);
  }

  /**
   * Placeholder for anti-aliased text rendering.
   */
  public void textAA(float x, float y, String s) {
    rect(x, y, 300, 24); // placeholder
  }

  /**
   * Renders text using a pre-baked bitmap font.
   *
   * <p>Each character is drawn as a textured quad. The font atlas is bound once
   * and appropriate texture coordinates are generated for each glyph.</p>
   */
  public void text(float x, float y, String s) {
    font.getTexture().bind();
    glBegin(GL_QUADS);
    float drawX = x;
    float drawY = y;
    for (char c : s.toCharArray()) {
      float u = font.getCharU(c);
      float v = font.getCharV(c);

      glTexCoord2f(u, v);
      glVertex2f(drawX, drawY);
      glTexCoord2f(u + 1f / 16f, v);
      glVertex2f(drawX + 8, drawY);
      glTexCoord2f(u + 1f / 16f, v + 1f / 8f);
      glVertex2f(drawX + 8, drawY + 16);
      glTexCoord2f(u, v + 1f / 8f);
      glVertex2f(drawX, drawY + 16);

      drawX += 8; // Move to the next character position
    }
    glEnd();
  }

  /**
   * Placeholder for any final rendering steps. Currently does nothing.
   */
  public void end() {
    // placeholder for any final rendering steps
  }

  /** Releases OpenGL resources held by this renderer. */
  public void dispose() {
    font.dispose();
  }
}
