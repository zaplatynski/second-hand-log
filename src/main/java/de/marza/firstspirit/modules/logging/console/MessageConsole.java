package de.marza.firstspirit.modules.logging.console;

import de.marza.firstspirit.modules.logging.console.utilities.LimitLinesDocumentListener;

import java.awt.Color;
import java.io.PrintStream;

import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 * The type Message console.
 * <p>Create a simple console to display text messages.</p>
 * <p>Messages can be directed here from different sources. Each source can have its messages
 * displayedin a different color.</p>
 * <p>Messages can either be appended to the console or inserted as the first line of the
 * console. You can limit the number of lines to hold in the Document.</p>
 */
public class MessageConsole {

  private JTextComponent textComponent;
  private Document document;
  private boolean append;
  private LimitLinesDocumentListener limitLines;

  /**
   * Instantiates a new Message console.
   *
   * @param textComponent the text component
   */
  public MessageConsole(final JTextComponent textComponent) {
    this(textComponent, true);
  }

  /**
   * Use the text component specified as a simply console to display text messages.
   *
   * <p>The messages can either be appended to the end of the console or inserted as the
   * first line of the console.</p>
   *
   * @param textComponent the text component
   * @param append      the is append
   */
  public MessageConsole(final JTextComponent textComponent, final boolean append) {
    if (textComponent == null) {
      throw new IllegalArgumentException("JTextComponent is null!");
    }
    this.textComponent = textComponent;
    this.document = textComponent.getDocument();
    this.append = append;
    textComponent.setEditable(false);
    textComponent.setBackground(Color.WHITE);
  }

  /**
   * Redirect the output from the standard output to the console using the specified color and
   * PrintStream. When a PrintStream is specified the message will be added to the Document before
   * it is also written to the PrintStream.
   *
   * @param textColor   the text color
   * @param printStream the print stream
   */
  public void redirectOut(final Color textColor, final PrintStream printStream) {
    final ConsoleOutputStream cos = new ConsoleOutputStream(this, textColor, printStream);
    System.setOut(new PrintStream(cos, true));
  }

  /**
   * Redirect the output from the standard output to the console using the default text color and
   * null PrintStream.
   */
  public void redirectOut() {
    redirectOut(null, null);
  }

  /**
   * Redirect the output from the standard error to the console using the default text color and
   * null PrintStream.
   */
  public void redirectErr() {
    redirectErr(null, null);
  }

  /**
   * Redirect err.
   *
   * <p>Redirect the output from the standard error to the console using the specified color and
   * PrintStream. When a PrintStream is specified the message will be added to the Document before
   * it is also written to the PrintStream.</p>
   *
   * @param textColor   the text color
   * @param printStream the print stream
   */
  public void redirectErr(final Color textColor, final PrintStream printStream) {
    final ConsoleOutputStream cos = new ConsoleOutputStream(this, textColor, printStream);
    System.setErr(new PrintStream(cos, true));
  }

  /**
   * Sets message lines.
   * <p>
   * To prevent memory from being used up you can control the number of lines to display in the
   * console.
   * </p>
   * This number can be dynamically changed, but the console will only be updated the next time the
   * Document is updated.
   *
   * @param lines the lines
   */
  public void setMaxMessageLines(final int lines) {
    if (limitLines != null) {
      document.removeDocumentListener(limitLines);
    }

    limitLines = new LimitLinesDocumentListener(lines, append);
    document.addDocumentListener(limitLines);
  }

  /**
   * Gets document.
   *
   * @return the document
   */
  public Document getDocument() {
    return document;
  }

  /**
   * Is append boolean.
   *
   * @return the boolean
   */
  public boolean isAppend() {
    return append;
  }

  /**
   * Gets text component.
   *
   * @return the text component
   */
  public JTextComponent getTextComponent() {
    return textComponent;
  }
}
