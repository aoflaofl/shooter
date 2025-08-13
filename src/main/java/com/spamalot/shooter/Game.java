package com.spamalot.shooter;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import com.spamalot.shooter.input.Gamepad;
import com.spamalot.shooter.render.GLUtils;
import com.spamalot.shooter.state.*;

public class Game {
	private final int width;
	private final int height;
	private final String title;
	private long window;
	private final StateMachine states = new StateMachine();
	private final Time time = new Time();

	public Game(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

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

	private void initWindow() {
		if (!glfwInit())
			throw new IllegalStateException("GLFW init failed");
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		window = glfwCreateWindow(width, height, title, 0, 0);
		if (window == 0)
			throw new IllegalStateException("Window creation failed");

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		GLUtils.create();
		glClearColor(0f, 0f, 0f, 1f);

		glfwSetKeyCallback(window, (win, key, sc, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
				glfwSetWindowShouldClose(win, true);
		});

		Gamepad.init();
	}

	private void initStates() {
		states.push(new MenuState(window, states));
	}
}
