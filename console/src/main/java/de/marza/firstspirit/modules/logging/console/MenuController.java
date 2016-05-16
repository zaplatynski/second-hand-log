package de.marza.firstspirit.modules.logging.console;

import de.marza.firstspirit.modules.logging.console.utilities.ClipboardAccess;
import de.marza.firstspirit.modules.logging.console.utilities.HyperlinkExecutor;
import de.marza.firstspirit.modules.logging.console.utilities.ReadTextFromFile;

import java.awt.event.ActionEvent;
import java.net.URI;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * The type Menu controller.
 */
public class MenuController implements Runnable {

  private static final ClipboardAccess CLIPBOARD_ACCESS = new ClipboardAccess();
  private static final ReadTextFromFile ABOUT_TEXT = new ReadTextFromFile("/about.txt");
  private final ActionEvent event;
  private final JFrame parent;
  private final ConsoleWindow consoleWindow;
  private final MessageConsole console;
  private final ResourceBundle menuLabels;

  /**
   * Instantiates a new Menu controller.
   *
   * @param event the event
   */
  public MenuController(final ActionEvent event) {
    this.event = event;
    consoleWindow = ConsoleWindow.getInstance();
    parent = consoleWindow.getFrame(null);
    menuLabels = consoleWindow.getMenuLabels();
    console = consoleWindow.getConsole();
  }

  @Override
  public void run() {

    final MenuActions menuAction = MenuActions.valueOf(event.getActionCommand());

    switch (menuAction) {
      case CLOSE:
        parent.setVisible(false);
        break;
      case COPY_LOG:
        CLIPBOARD_ACCESS.copy(consoleWindow.getLogMessages());
        break;
      case CUT_LOG:
        CLIPBOARD_ACCESS.copy(consoleWindow.getLogMessages()); // let it fall through
      case CLEAR_LOG:
        consoleWindow.clearLog();
        parent.repaint();
        break;
      case SHOW_INFO:
        final String title = menuLabels.getString("aboutItem");
        JOptionPane.showMessageDialog(parent, ABOUT_TEXT.readAsJEditor(), title,
            JOptionPane.PLAIN_MESSAGE);
        break;
      case SHOW_BUGS_FEATURES:
        final URI issues = URI.create("https://github.com/zaplatynski/second-hand-log/issues");
        HyperlinkExecutor.browseTo(issues);
        break;
      case SHOW_HELP_CONTENTS:
        final URI wiki = URI.create("https://github.com/zaplatynski/second-hand-log/wiki");
        HyperlinkExecutor.browseTo(wiki);
        break;
      case SHOW_LOG_LINES_100:
      case SHOW_LOG_LINES_500:
      case SHOW_LOG_LINES_1K:
      case SHOW_LOG_LINES_2K:
      case SHOW_LOG_LINES_5K:
      case SHOW_LOG_LINES_10K:
        final int maxLogLines = menuAction.getLines(); //NOPMD
        console.setMaxMessageLines(maxLogLines);
        parent.repaint();
        break;
      default:
        final String errorMessage = menuLabels.getString("missingMenuAction");
        final String errorTitle = menuLabels.getString("unkownMenuCommand");
        JOptionPane.showMessageDialog(parent, errorMessage, errorTitle, JOptionPane.ERROR_MESSAGE);
    }
  }

}
