package com.spamalot.shooter.render;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

/**
 * Represents an OpenGL texture object.
 *
 * <p>Textures are loaded using the STB image library and uploaded to the GPU via
 * LWJGL's OpenGL bindings. The class encapsulates the OpenGL texture identifier
 * and provides simple bind/dispose helpers.</p>
 */
public class Texture {
  /** OpenGL handle to the texture object. */
  private final int id;
  /** Width of the texture in pixels. */
  private final int width;
  /** Height of the texture in pixels. */
  private final int height;

  /**
   * Loads a texture from disk and creates the corresponding OpenGL resource.
   *
   * <p>Image data is read through the STB library and then uploaded with
   * {@code glTexImage2D}. Various parameters such as filtering and wrapping are
   * configured to produce a straightforward, non-mipmapped texture.</p>
   *
   * @param path path to the image file on disk
   * @return a new {@code Texture} instance representing the loaded image
   */
  public static Texture load(String path) {
    IntBuffer w = BufferUtils.createIntBuffer(1);
    IntBuffer h = BufferUtils.createIntBuffer(1);
    IntBuffer comp = BufferUtils.createIntBuffer(1);
    STBImage.stbi_set_flip_vertically_on_load(true);
    ByteBuffer img = STBImage.stbi_load(path, w, h, comp, 4);
    if (img == null) {
      throw new RuntimeException("Failed to load image: " + path);
    }

    // Generate and bind a new texture object
    int tex = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, tex);
    // Configure filtering and wrapping
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    // Upload pixel data to the GPU
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, w.get(0), h.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, img);
    STBImage.stbi_image_free(img);

    return new Texture(tex, w.get(0), h.get(0));
  }

  /** Constructs a texture from an existing OpenGL handle. */
  private Texture(int id, int width, int height) {
    this.id = id;
    this.width = width;
    this.height = height;
  }

  /**
   * Binds this texture to the active texture unit.
   */
  public void bind() {
    glBindTexture(GL_TEXTURE_2D, id);
  }

  /**
   * Deletes the OpenGL texture object.
   */
  public void dispose() {
    glDeleteTextures(id);
  }

  /** @return the width of the texture in pixels. */
  public int width() {
    return width;
  }

  /** @return the height of the texture in pixels. */
  public int height() {
    return height;
  }
}
