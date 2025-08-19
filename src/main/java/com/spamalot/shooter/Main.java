package com.spamalot.shooter;

/**
 * The entry point for the Shooter Demo application.
 *
 * <p>This class contains the main method which initializes and starts the game.
 * </p>
 */
public class Main {
  /**
   * Main method to launch the Shooter Demo game.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    new Game(1280, 720, "Shooter Demo").run();
  }
}
