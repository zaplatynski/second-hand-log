package de.marza.firstspirit.modules.logging.console.utilities;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JEditorPane;
import javax.swing.UIManager;


/**
 * The type Read text from file.
 */
public class ReadTextFromFile {

  private static final Logger LOGGER = Logger.getInstance();
  private final String filePath;
  private JEditorPane message;

  /**
   * Instantiates a new Read text from file.
   *
   * @param filePath the file path
   */
  public ReadTextFromFile(final String filePath) {
    if (filePath == null || filePath.trim().isEmpty()) {
      throw new IllegalArgumentException("filePath null or empty");
    }
    this.filePath = filePath;
  }

  /**
   * Read as j editor j editor pane.
   *
   * @return the j editor pane
   */
  public JEditorPane readAsJEditor() {
    if (message == null) {
      final InputStream inputStream = getClass().getResourceAsStream(filePath); //NOPMD
      final InputStreamReader streamReader = new InputStreamReader(inputStream);
      try (final BufferedReader txtReader = new BufferedReader(streamReader)) {
        String line;
        final StringBuilder buffer = new StringBuilder();
        while ((line = txtReader.readLine()) != null) {
          buffer.append(line);
        }
        message = new JEditorPane("text/html", buffer.toString());
        message.setEditable(false);
        final Color backgrounfdColor = UIManager.getColor("Panel.background");
        message.setBackground(backgrounfdColor);
        message.addHyperlinkListener(new HyperlinkExecutor());
      } catch (final IOException exception) {
        LOGGER.logError("An logError occurred while reading the about text: %s",
            exception.toString());
      }
    }
    return message;
  }
}
