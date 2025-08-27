package com.spamalot.shooter.state;

import com.spamalot.shooter.audio.Sound;
import com.spamalot.shooter.ecs.Bullet;
import com.spamalot.shooter.ecs.Player;
import com.spamalot.shooter.input.Gamepad;
import com.spamalot.shooter.math.Vec2f;
import com.spamalot.shooter.render.Renderer2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Core gameplay state where the player controls the character.
 *
 * <p>Input is read through the {@link Gamepad} class and translated into
 * movement or firing actions. Rendering is performed using a simple
 * {@link Renderer2D}. A shooting sound effect is played when the fire action is
 * held.</p>
 */
public class GameplayState implements GameState {
  private final Renderer2D renderer = new Renderer2D();
  private final Player player = new Player();
  private final Sound shootSound = Sound.load("/assets/sounds/shoot.wav");
  private final List<Bullet> bullets = new ArrayList<>();
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;
  private static final int WORLD_WIDTH = WIDTH * 4;
  private static final int WORLD_HEIGHT = HEIGHT * 4;
  private Vec2f cam = Vec2f.ZERO;

  private static final int TILE_SIZE = 128;
  private static final Vec2f[] STAR_TILE = {
      new Vec2f(10, 10),
      new Vec2f(40, 80),
      new Vec2f(90, 20),
      new Vec2f(120, 100)
  };

  /**
   * Constructs the gameplay state. Parameters are currently unused but mirror
   * those of other states for future expansion.
   */
  public GameplayState(long window, StateMachine sm) {
  }

  /**
   * Updates game logic such as player movement and shooting.
   *
   * <p>Polling the {@link Gamepad} yields axis values for movement and button
   * states for firing. The state machine itself is not modified here but the
   * player entity reacts to the input.</p>
   */
  @Override
  public void update(double dt) {
    Gamepad.poll();
    var move = Gamepad.axis("MOVE");
    player.move(move, dt);
    player.wrap(WORLD_WIDTH, WORLD_HEIGHT);
    cam = new Vec2f(player.pos().x() - WIDTH / 2f, player.pos().y() - HEIGHT / 2f);
    cam = new Vec2f((cam.x() % WORLD_WIDTH + WORLD_WIDTH) % WORLD_WIDTH,
        (cam.y() % WORLD_HEIGHT + WORLD_HEIGHT) % WORLD_HEIGHT);

    if (Gamepad.actionJustPressed("FIRE")) {
      Vec2f dir = player.facing();
      bullets.add(new Bullet(player.pos(), dir.mul(700)));
      if (!shootSound.isPlaying()) {
        shootSound.play();
      }
    }

    Iterator<Bullet> it = bullets.iterator();
    while (it.hasNext()) {
      Bullet b = it.next();
      b.update(dt);
      float bx = worldToScreenX(b.pos().x());
      float by = worldToScreenY(b.pos().y());
      if (bx < 0 || bx > WIDTH || by < 0 || by > HEIGHT) {
        it.remove();
      }
    }
  }

  /**
   * Renders the player sprite to the screen.
   */
  @Override
  public void render() {
    renderer.begin();
    renderBackground();
    player.draw(renderer, WIDTH / 2f, HEIGHT / 2f);
    for (Bullet b : bullets) {
      float bx = worldToScreenX(b.pos().x());
      float by = worldToScreenY(b.pos().y());
      b.draw(renderer, bx, by);
    }
    renderer.end();
  }

  /**
   * Frees OpenGL and audio resources.
   */
  @Override
  public void dispose() {
    renderer.dispose();
    shootSound.dispose();
  }

  private float worldToScreenX(float worldX) {
    float x = worldX - cam.x();
    if (x < 0) {
      x += WORLD_WIDTH;
    } else if (x >= WORLD_WIDTH) {
      x -= WORLD_WIDTH;
    }
    return x;
  }

  private float worldToScreenY(float worldY) {
    float y = worldY - cam.y();
    if (y < 0) {
      y += WORLD_HEIGHT;
    } else if (y >= WORLD_HEIGHT) {
      y -= WORLD_HEIGHT;
    }
    return y;
  }

  private void renderBackground() {
    float offsetX = cam.x() % TILE_SIZE;
    float offsetY = cam.y() % TILE_SIZE;
    int tilesX = WIDTH / TILE_SIZE + 2;
    int tilesY = HEIGHT / TILE_SIZE + 2;
    for (int y = 0; y < tilesY; y++) {
      for (int x = 0; x < tilesX; x++) {
        float baseX = x * TILE_SIZE - offsetX;
        float baseY = y * TILE_SIZE - offsetY;
        for (Vec2f star : STAR_TILE) {
          renderer.rect(baseX + star.x(), baseY + star.y(), 2, 2);
        }
      }
    }
  }
}
