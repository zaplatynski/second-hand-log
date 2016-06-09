package de.marza.firstspirit.modules.logging.console;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;


/**
 * The type Console view.
 */
public class ConsoleView {

  private final int maxLogLines;
  private final JTextPane textPane;
  private final MessageConsole console;

  /**
   * Instantiates a new Console view.
   *
   * @param maxLogLines the max log lines
   */
  public ConsoleView(final int maxLogLines) {
    this.maxLogLines = maxLogLines;
    textPane = new JTextPane();
    console = new MessageConsole(textPane);
  }

  /**
   * Gets max log lines.
   *
   * @return the max log lines
   */
  public int getMaxLogLines() {
    return maxLogLines;
  }

  public JTextPane getTextPane() {
    return textPane;
  }

  public MessageConsole getConsole() {
    return console;
  }

  public void init(final Container window) {
    setupLogView(window);
    setupConsole();
  }


  private void setupLogView(final Container container) {
    if (container == null) {
      throw new IllegalArgumentException("Container is null");
    }
    textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    container.add(new JScrollPane(textPane));
  }

  private void setupConsole() {
    console.redirectErr(System.err);
    console.redirectOut(System.out);
    console.setMaxMessageLines(maxLogLines);
  }
}
