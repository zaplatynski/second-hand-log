package de.marza.firstspirit.modules.logging.console.utilities;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * The type Hyperlink executor.
 */
public class HyperlinkExecutor implements HyperlinkListener {

  private static boolean isClicked(final HyperlinkEvent event) {
    return event.getEventType() == HyperlinkEvent.EventType.ACTIVATED;
  }

  /**
   * Browse to URI.
   *
   * @param uri the address the browser schould go to.
   */
  public static void browseTo(final URI uri) {
    if (uri != null && Desktop.isDesktopSupported()) {
      try {
        Desktop.getDesktop().browse(uri);
      } catch (final IOException exception) {
        System.err.println("An error occurred while browse to '" + uri.toString() + "':"
            + exception.toString());
        exception.printStackTrace(System.err);
      }
    }
  }

  @Override
  public void hyperlinkUpdate(final HyperlinkEvent event) {
    URI uri = null;
    try {
      uri = event.getURL().toURI();
    } catch (final URISyntaxException exception) {
      System.err.println("An error occurred while retrieving URI:" + exception.toString());
      exception.printStackTrace(System.err);
    }
    if (isClicked(event)) {
      browseTo(uri);
    }
  }


}
