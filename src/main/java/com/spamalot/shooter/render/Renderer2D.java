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

public class Renderer2D {
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;
  private BitmapFont font;

  public Renderer2D() {
    OpenGlInitializer.create();
    OpenGlInitializer.ortho(WIDTH, HEIGHT);
    glDisable(GL_DEPTH_TEST);
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    // Load the bitmap font
    font = new BitmapFont("assets/textures/font.png");
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

  public void textAA(float x, float y, String s) {
    rect(x, y, 300, 24); // placeholder
  }

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

  public void end() {
    // placeholder for any final rendering steps
  }

  public void dispose() {
    font.dispose();
  }
}
