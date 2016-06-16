package de.marza.firstspirit.modules.logging.console;

import org.junit.Test;

import java.awt.GraphicsEnvironment;
import java.security.SecureRandom;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 * The type Console window test.
 */
public class ConsoleWindowTest {

  private ResourceBundle menuLabels = ResourceBundle.getBundle("de.marza.firstspirit.modules"
      + ".logging.MenuLabels");

  /**
   * Main.
   *
   * @param args the args
   * @throws Exception the exception
   */
  public static void main(final String[] args) throws Exception {

    ConsoleWindow.getInstance().getFrame("Test").setDefaultCloseOperation(WindowConstants
        .EXIT_ON_CLOSE);

    ConsoleWindow.getInstance().show("Test");

    new Thread(new Runnable(){
      @Override
      public void run() {
        try {
          simulateLogEvents(ConsoleWindow.getInstance().getFrame(null));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }
    }).start();

  }

  private static void simulateLogEvents(final JFrame frame) throws InterruptedException {

    final SecureRandom random = new SecureRandom();
    for (int i = 0; i < 10000; i++) {
      String message = "";//NOPMD
      if (i % 2 == 1) {
        message += "DEBUG ";
      }
      if (i % 3 == 0) {
        message += "INFO ";
      }
      if (i % 5 == 0) {
        message += "WARN ";
      }
      if (i % 7 == 0) {
        message += "ERROR ";
      }
      message +=i + ". test message ";
      System.out.println(message + " " + UUID.randomUUID()+ " " + UUID.randomUUID()); //NOPMD
      Thread.currentThread().sleep(75L * random.nextInt(1));
      System.err.println(++i + ". logError"); //NOPMD
      Thread.currentThread().sleep(500L * random.nextInt(2));
      if (!frame.isVisible()) {
        System.exit(0);
      }
    }
  }

  /**
   * Test window title with custom part.
   *
   * @param <T> the type parameter
   * @throws Exception the exception
   */
  @Test
  public <T> void testWindowTitleWithCustomPart() throws Exception {
    assumeThat(GraphicsEnvironment.isHeadless(), is(false));

    final JFrame frame = ConsoleWindow.getInstance().getFrame("Part of Window Tile");

    final String expectedTitle = menuLabels.getString("appName") + " / Part of Window Tile";
    assertThat("Expect specific value", frame.getTitle(), is(expectedTitle));
  }
}
