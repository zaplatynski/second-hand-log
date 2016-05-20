package de.marza.firstspirit.modules.logging.console.utilities;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;


/**
 * The type Log clipboard onwer.
 */
public class LogClipboardOnwer implements ClipboardOwner {

  private static LogClipboardOnwer self = new LogClipboardOnwer();

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static ClipboardOwner getInstance() {
    return self;
  }

  @Override
  public void lostOwnership(final Clipboard clipboard, final Transferable contents) {
    //who cares?
  }
}
