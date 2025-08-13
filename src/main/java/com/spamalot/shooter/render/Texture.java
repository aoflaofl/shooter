package com.spamalot.shooter.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class Texture {
    private final int id;
    private final int width, height;

    public static Texture load(String path) {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);
        STBImage.stbi_set_flip_vertically_on_load(true);
        ByteBuffer img = STBImage.stbi_load(path, w, h, comp, 4);
        if (img == null) throw new RuntimeException("Failed to load image: " + path);

        int tex = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, tex);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, w.get(0), h.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, img);
        STBImage.stbi_image_free(img);

        return new Texture(tex, w.get(0), h.get(0));
    }

    private Texture(int id, int width, int height) { this.id=id; this.width=width; this.height=height; }
    public void bind() { glBindTexture(GL_TEXTURE_2D, id); }
    public void dispose() { glDeleteTextures(id); }
    public int width() { return width; }
    public int height() { return height; }
}
