package com.spamalot.shooter.render;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public final class GLUtils {
  private GLUtils() {
  }

  public static void create() {
    GL.createCapabilities();
  }

  public static void ortho(int w, int h) {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, w, h, 0, -1, 1);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
  }
}
