package com.spamalot.shooter.render;

import static org.lwjgl.opengl.GL11.*;

public class Renderer2D {
  private final int width = 1280;
  private final int height = 720;

  public Renderer2D() {
    GLUtils.create();
    GLUtils.ortho(width, height);
    glDisable(GL_DEPTH_TEST);
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  public void begin() {
    glLoadIdentity();
  }

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

  public void text(float x, float y, String s) {
    rect(x, y, 300, 24); // placeholder
  }

  public void end() {
  }

  public void dispose() {
  }
}
