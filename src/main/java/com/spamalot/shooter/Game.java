package com.spamalot.shooter;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import com.spamalot.shooter.input.Gamepad;
import com.spamalot.shooter.render.OpenGlInitializer;
import com.spamalot.shooter.state.MenuState;
import com.spamalot.shooter.state.StateMachine;

/**
 * Represents the main game application.
 * 
 * <p>Handles window creation, game loop, state management, and resource
 * cleanup.</p>
 */
public class Game {
  /** Width of the game window. */
  private final int width;
  /** Height of the game window. */
  private final int height;
  /** Title of the game window. */
  private final String title;
  /** GLFW window handle. */
  private long window;
  /** State machine managing game states. */
  private final StateMachine states = new StateMachine();
  /** Time management for frame updates. */
  private final Time time = new Time();

  /**
   * Constructs a new Game instance.
   *
   * @param width  the width of the window
   * @param height the height of the window
   * @param title  the title of the window
   */
  public Game(int width, int height, String title) {
    this.width = width;
    this.height = height;
    this.title = title;
  }

  /**
   * Starts the game loop and manages the application lifecycle.
   */
  public void run() {
    initWindow();
    initStates();

    while (!glfwWindowShouldClose(window)) {
      time.tick();
      glfwPollEvents();

      states.update(time.delta());
      glClear(GL_COLOR_BUFFER_BIT);
      states.render();

      glfwSwapBuffers(window);
    }

    states.dispose();
    glfwDestroyWindow(window);
    glfwTerminate();
  }

  /**
   * Initializes the GLFW window and OpenGL context.
   * 
   * <p>Sets window hints, creates the window, sets up key callbacks, and
   * initializes gamepad input.</p>
   */
  private void initWindow() {
    if (!glfwInit()) {
      throw new IllegalStateException("GLFW init failed");
    }
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
    window = glfwCreateWindow(width, height, title, 0, 0);
    if (window == 0) {
      throw new IllegalStateException("Window creation failed");
    }
    glfwMakeContextCurrent(window);
    glfwSwapInterval(1);
    OpenGlInitializer.create();
    glClearColor(0f, 0f, 0f, 1f);

    // Set key callback to close window on ESC key press
    glfwSetKeyCallback(window, (win, key, sc, action, mods) -> {
      if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
        glfwSetWindowShouldClose(win, true);
      }
    });

    Gamepad.init();
  }

  /**
   * Initializes the game states by pushing the main menu state.
   */
  private void initStates() {
    states.push(new MenuState(window, states));
  }
}
