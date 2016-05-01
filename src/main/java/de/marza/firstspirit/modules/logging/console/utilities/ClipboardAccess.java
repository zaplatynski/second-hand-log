package de.marza.firstspirit.modules.logging.console.utilities;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


/**
 * The type Clipboard access.
 */
public class ClipboardAccess {
  private final Clipboard clipboard;

  /**
   * Instantiates a new Clipboard access.
   */
  public ClipboardAccess() {
    clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
  }

  /**
   * Copy string.
   *
   * @param logMessages the log messages
   */
  public void copy(final String logMessages) {
    final StringSelection stringSelection = new StringSelection(logMessages);
    clipboard.setContents(stringSelection, LogClipboardOnwer.getInstance());
  }

  /**
   * Paste string.
   *
   * @return the string
   */
  public String paste() {
    try {
      final Transferable contents = clipboard.getContents(LogClipboardOnwer.getInstance());
      return (String) contents.getTransferData(DataFlavor.stringFlavor);
    } catch (UnsupportedFlavorException | IOException error) {
      System.err.println("Error occurred while accessing clipboard: " + error.toString());
      return "";
    }
  }
}
