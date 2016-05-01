package de.marza.firstspirit.modules.logging.console;

import de.marza.firstspirit.modules.logging.console.utilities.HyperlinkExecutor;
import de.marza.firstspirit.modules.logging.console.utilities.LogClipboardOnwer;
import de.marza.firstspirit.modules.logging.console.utilities.ReadTextFromFile;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * The type Menu controller.
 */
public class MenuController implements Runnable {

  private final ActionEvent event;
  private final ReadTextFromFile readAboutText;


  /**
   * Instantiates a new Menu controller.
   *
   * @param event the event
   */
  public MenuController(final ActionEvent event) {
    this.event = event;
    readAboutText = new ReadTextFromFile("/about.txt");
  }

  private static void copyToClipboard(final String logMessages) {
    final StringSelection stringSelection = new StringSelection(logMessages);
    final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, LogClipboardOnwer.getInstance());
  }

  @Override
  public void run() {
    final ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    final JFrame parent = consoleWindow.getFrame();
    final MenuActions menuAction = MenuActions.valueOf(event.getActionCommand());
    switch (menuAction) {
      case CLOSE:
        parent.setVisible(false);
        break;
      case COPY_LOG:
        copyToClipboard(consoleWindow.getLogMessages());
        break;
      case CUT_LOG:
        copyToClipboard(consoleWindow.getLogMessages()); // let it fall through
      case CLEAR_LOG:
        consoleWindow.clearLog();
        parent.repaint();
        break;
      case SHOW_INFO:
        final String title = consoleWindow.getMenuLabels().getString("aboutItem");
        JOptionPane.showMessageDialog(parent, readAboutText.readAsJEditor(), title,
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
      case SHOW_LOG_LINES_1K:
      case SHOW_LOG_LINES_2K:
      case SHOW_LOG_LINES_5K:
      case SHOW_LOG_LINES_10K:
        consoleWindow.getConsole().setMessageLines(menuAction.getLines());
        parent.repaint();
        break;
      default:
        final String errorMessage = consoleWindow.getMenuLabels().getString("missingMenuAction");
        final String errorTitle = consoleWindow.getMenuLabels().getString("unkownMenuCommand");
        JOptionPane.showMessageDialog(parent, errorMessage, errorTitle, JOptionPane.ERROR_MESSAGE);
    }
  }

}
