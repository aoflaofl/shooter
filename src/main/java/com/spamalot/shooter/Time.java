package com.spamalot.shooter;

public class Time {
    private double lastTime = glfwTime();
    private double delta;

    public void tick() {
        double now = glfwTime();
        delta = Math.min(1.0/30.0, now - lastTime);
        lastTime = now;
    }
    public double delta() { return delta; }
    private static double glfwTime() { return org.lwjgl.glfw.GLFW.glfwGetTime(); }
}
