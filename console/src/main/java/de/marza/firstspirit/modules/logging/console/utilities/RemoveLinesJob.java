package de.marza.firstspirit.modules.logging.console.utilities;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;


/**
 * The type Remove lines job.
 */
public class RemoveLinesJob implements Runnable {
  private final DocumentEvent event;
  private final int maximumLines;
  private final boolean isRemoveFromStart;

  /**
   * Instantiates a new Remove lines job.
   *
   * @param documentListener the limit lines document listener
   * @param event            the event
   */
  public RemoveLinesJob(final LimitLinesDocumentListener documentListener,
                        final DocumentEvent event) {
    maximumLines = documentListener.getMaximumLines();
    isRemoveFromStart = documentListener.isRemoveFromStart();
    this.event = event;
  }

  /**
   * Remove lines from the start of the Document.
   */
  private static void removeFromStart(final Document document, final Element root) {
    final Element line = root.getElement(0);
    final int end = line.getEndOffset(); //NOPMD

    try {
      document.remove(0, end);
    } catch (final BadLocationException ble) { //NOPMD
      //ignore to avoid stack overflows
    }
  }

  /**
   * Remove lines from the end of the Document.
   */
  private static void removeFromEnd(final Document document, final Element root) {
    //  We use start minus 1 to make sure we remove the newline
    //  character of the previous line

    final int index = root.getElementCount() - 1;
    final Element line = root.getElement(index);
    final int start = line.getStartOffset(); //NOPMD
    final int end = line.getEndOffset(); //NOPMD

    try {
      document.remove(start - 1, end - start);
    } catch (final BadLocationException ble) { //NOPMD
      //ignore to avoid stack overflows
    }
  }

  @Override
  public void run() {
    removeLines(event);
  }

  /**
   * Remove lines from the Document when necessary.
   */
  private void removeLines(final DocumentEvent event) {
    //  The root Element of the Document will tell us the total number
    //  of line in the Document.

    final Document document = event.getDocument();
    final Element root = document.getDefaultRootElement(); //NOPMD


    while (root.getElementCount() > maximumLines) { //NOPMD
      if (isRemoveFromStart) {
        removeFromStart(document, root);
      } else {
        removeFromEnd(document, root);
      }
    }
  }
}
