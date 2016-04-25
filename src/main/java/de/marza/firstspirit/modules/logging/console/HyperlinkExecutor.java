package de.marza.firstspirit.modules.logging.console;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HyperlinkExecutor implements HyperlinkListener {

    @Override
    public void hyperlinkUpdate(final HyperlinkEvent e) {
        URI uri = null;
        try {
            uri = e.getURL().toURI();
        } catch (final URISyntaxException exception) {
            System.err.println("An error occurred while retrieving URI:" + exception.toString());
            exception.printStackTrace(System.err);
        }
        browseTo(e, uri);
    }

    private void browseTo(final HyperlinkEvent e, final URI uri) {
        if (uri != null && Desktop.isDesktopSupported() && isClicked(e)) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (final IOException exception) {
                System.err.println("An error occurred while browse to '" + uri.toString() + "':" + exception.toString());
                exception.printStackTrace(System.err);
            }
        }
    }

    private static boolean isClicked(final HyperlinkEvent e) {
        return e.getEventType() == HyperlinkEvent.EventType.ACTIVATED;
    }
}
