package de.marza.firstspirit.modules.logging.console.utilities;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * The type Hyperlink executor.
 */
public final class HyperlinkExecutor implements HyperlinkListener {

  private static final Logger LOGGER = Logger.getInstance();

  public HyperlinkExecutor() {
    //empty
  }

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
        final Desktop desktop = Desktop.getDesktop();
        desktop.browse(uri); //NOPMD
      } catch (final IOException exception) {
        LOGGER.logError("An logError occurred while browse to '%s': %s", uri.toString(),
            exception.toString());
      }
    }
  }

  @Override
  public void hyperlinkUpdate(final HyperlinkEvent event) {
    URI uri = null;
    try {
      final URL url = event.getURL();
      uri = url.toURI(); //NOPMD
    } catch (final URISyntaxException exception) {
      LOGGER.logError("An logError occurred while retrieving URI: %s", exception);
    }
    if (isClicked(event)) {
      browseTo(uri);
    }
  }


}
