package de.marza.firstspirit.modules.logging.console.utilities;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;


/**
 * The type Log clipboard onwer.
 */
public final class LogClipboardOnwer implements ClipboardOwner {

  private static final LogClipboardOnwer SELF = new LogClipboardOnwer();

  private LogClipboardOnwer() {
    //empty
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static ClipboardOwner getInstance() {
    return SELF;
  }

  @Override
  public void lostOwnership(final Clipboard clipboard, final Transferable contents) {
    //who cares?
  }
}
