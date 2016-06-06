package de.marza.firstspirit.modules.logging.console;

import de.marza.firstspirit.modules.logging.console.utilities.Level;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;

/**
 * Class to intercept output from a PrintStream and add it to a Document. <p>The output can
 * optionally be redirected to a different PrintStream. The text displayed in the Document can be
 * color coded to indicate the output source.</p>
 */
public class ConsoleOutputStream extends ByteArrayOutputStream {

  private final String endOfLine;
  private final StringBuilder buffer; //NOPMD
  private final Document document;
  private final JTextComponent textComponent;
  private final boolean append;
  private final PrintStream printStream;
  private final Level defaultLevel;
  private Level level;
  private boolean firstLine;

  /**
   * Specify the option text color and PrintStream.
   *
   * @param messageConsole the message console
   * @param defaultLevel   the default level
   * @param printStream    the print stream
   */
  public ConsoleOutputStream(final MessageConsole messageConsole, final Level defaultLevel,
                             final PrintStream printStream) {
    endOfLine = System.lineSeparator();
    buffer = new StringBuilder(80); //NOPMD
    textComponent = messageConsole.getTextComponent();
    document = textComponent.getDocument();
    append = messageConsole.isAppend();
    firstLine = append;
    this.defaultLevel = defaultLevel;
    level = defaultLevel;
    this.printStream = printStream;
  }

  /**
   * Override this method to intercept the output text. Each line of text output will actually
   * involve invoking this method twice: <br> a) for the actual text message<br> b) for the newLine
   * string<br> <br> The message will be treated differently depending on whether the line will be
   * appended or inserted into the Document
   */
  @Override
  public void flush() {
    final String message = toString();

    if (message.length() == 0) { //NOPMD
      return;
    }

    if (append) {
      handleAppend(message);
    } else {
      handleInsert(message);
    }

    reset();
  }

  /**
   * We don't want to have blank lines in the Document. <p>The first line added will simply be the
   * message. For additional lines it will be: newLine + message.</p>
   */
  private void handleAppend(final String message) {
    // This check is needed in case the text in the Document has been
    // cleared. The buffer may contain the endOfLine string from the previous
    // message.

    if (document.getLength() == 0) {
      buffer.setLength(0);
    }

    level = level.valueOfFragment(message);

    if (endOfLine.equals(message)) {
      buffer.append(message);
    } else {
      buffer.append(message);
      clearBuffer();
    }

  }

  /**
   * We don't want to merge the new message with the existing message so the line will be inserted
   * as: message + newLine.
   */
  private void handleInsert(final String message) {
    level = level.valueOfFragment(message);
    buffer.append(message);

    if (endOfLine.equals(message)) {
      clearBuffer();
    }
  }

  /**
   * The message and the newLine have been added to the buffer in the appropriate order so we can
   * now update the Document and send the text to the optional PrintStream.
   */
  private void clearBuffer() {
    //  In case both the standard out and standard err are being redirected
    //  we need to insert a newline character for the first line only

    if (firstLine && document.getLength() != 0) {
      buffer.insert(0, '\n');
    }

    firstLine = false;
    final String line = buffer.toString();

    try {
      final SimpleAttributeSet textStyle = level.getAttributes();
      level = defaultLevel;
      if (append) {
        final int offset = document.getLength();
        document.insertString(offset, line, textStyle);
        final int newLength = document.getLength();
        textComponent.setCaretPosition(newLength);
      } else {
        document.insertString(0, line, textStyle);
        textComponent.setCaretPosition(0);
      }
    } catch (final BadLocationException ble) { //NOPMD
      //ignore to avoid stack overflows
    }

    if (printStream != null) {
      printStream.print(line);
    }

    buffer.setLength(0);
  }
}
