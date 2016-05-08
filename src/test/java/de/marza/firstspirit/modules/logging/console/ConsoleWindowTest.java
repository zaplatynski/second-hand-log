package de.marza.firstspirit.modules.logging.console;

import org.junit.Test;

import java.awt.GraphicsEnvironment;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 * The type Console window test.
 */
public class ConsoleWindowTest {

  private ResourceBundle menuLabels = ResourceBundle.getBundle("de.marza.firstspirit.modules" +
      ".logging.MenuLabels");

  /**
   * Main.
   *
   * @param args the args
   * @throws Exception the exception
   */
  public static void main(final String[] args) throws Exception {

    ConsoleWindow.getInstance().getFrame("Test").setDefaultCloseOperation(WindowConstants
        .EXIT_ON_CLOSE);

    ConsoleWindow.getInstance().show(null);

    simulateLogEvents(ConsoleWindow.getInstance().getFrame(null));
  }

  private static void simulateLogEvents(final JFrame frame) throws InterruptedException {
    final Random random = new Random();
    for (int i = 0; i < 10000; i++) {
      System.out.println(i + ". test message");
      Thread.currentThread().sleep(75 * random.nextInt(1));
      System.err.println(++i + ". error");
      Thread.currentThread().sleep(500 * random.nextInt(2));
      if (!frame.isVisible()) {
        System.exit(0);
      }
    }
  }

  @Test
  public <T> void testWindowTitleWithCustomPart() throws Exception {
    assumeThat(GraphicsEnvironment.isHeadless(), is(false));

    final JFrame frame = ConsoleWindow.getInstance().getFrame("Part of Window Tile");

    final String expectedTitle = menuLabels.getString("appName") + " / Part of Window Tile";
    assertThat(frame.getTitle(), is(expectedTitle));
  }
}
