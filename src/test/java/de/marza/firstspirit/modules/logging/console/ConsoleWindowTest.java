package de.marza.firstspirit.modules.logging.console;

import javax.swing.JFrame;

/**
 * The type Console window test.
 */
public class ConsoleWindowTest {

  /**
   * Main.
   *
   * @param args the args
   * @throws Exception the exception
   */
  public static void main(final String[] args) throws Exception {

    ConsoleWindow.getInstance().getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    ConsoleWindow.getInstance().show();

    simulateLogEvents(ConsoleWindow.getInstance().getFrame());
  }

  private static void simulateLogEvents(final JFrame frame) throws InterruptedException {
    for (int i = 0; i < 10000; i++) {
      System.out.println(i + ". test message");
      Thread.currentThread().sleep(75);
      System.err.println(++i + ". error");
      Thread.currentThread().sleep(500);
      if (!frame.isVisible()) {
        System.exit(0);
      }
    }
  }
}
