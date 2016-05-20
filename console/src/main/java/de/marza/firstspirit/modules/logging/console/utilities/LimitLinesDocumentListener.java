package de.marza.firstspirit.modules.logging.console.utilities;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A class to control the maximum number of lines to be stored in a Document. <p> Excess lines can
 * be removed from the start or end of the Document depending on your requirement. </p> a) if you
 * append text to the Document, then you would want to remove lines from the start. b) if you insert
 * text at the beginning of the Document, then you would want to remove lines from the end.
 */
public class LimitLinesDocumentListener implements DocumentListener {

  private final boolean removeFromStart;
  private final int maximumLines;

  /**
   * Specify the number of lines to be stored in the Document. Extra lines will be removed from the
   * start or end of the Document, depending on the boolean value specified.
   *
   * @param maximumLines      the maximum lines
   * @param removeFromStart the is remove from start
   */
  public LimitLinesDocumentListener(final int maximumLines, final boolean removeFromStart) {
    if (maximumLines < 1) {
      final String message = "Maximum lines must be greater than 0";
      throw new IllegalArgumentException(message);
    }
    this.maximumLines = maximumLines;
    this.removeFromStart = removeFromStart;
  }

  /**
   * Return the maximum number of lines to be stored in the Document.
   *
   * @return the limit lines
   */
  public int getMaximumLines() {
    return maximumLines;
  }

  @Override
  public void insertUpdate(final DocumentEvent event) {
    //  Changes to the Document can not be done within the listener
    //  so we need to add the processing to the end of the EDT
    SwingUtilities.invokeLater(new RemoveLinesJob(this, event));
  }

  @Override
  public void removeUpdate(final DocumentEvent event) {
    //not needed
  }

  @Override
  public void changedUpdate(final DocumentEvent event) {
    //not needed
  }

  /**
   * Is remove from start activated.
   *
   * @return true if yes
   */
  public boolean isRemoveFromStart() {
    return removeFromStart;
  }
}
