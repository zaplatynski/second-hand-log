package de.marza.firstspirit.modules.logging.console;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;


/**
 * The type Console menu.
 */
public class ConsoleMenu {


  private final String defaultIso3Code;
  private final String germanIso3Code;
  private final ResourceBundle menuLabels;
  private final ConsoleView view;

  /**
   * Instantiates a new Console menu.
   *
   * @param view the view
   */
  public ConsoleMenu(final ConsoleView view) {
    this.view = view;
    menuLabels = ResourceBundle.getBundle("de.marza.firstspirit.modules.logging.MenuLabels");
    defaultIso3Code = Locale.getDefault().getISO3Language();
    germanIso3Code = Locale.GERMAN.getISO3Language();
  }

  /**
   * Sets menu.
   *
   * @param window the window
   */
  public void setupMenu(final JFrame window) {
    final MenuActionListener menuActionLisener = new MenuActionListener();

    final JMenuBar menubar = new JMenuBar();
    window.setJMenuBar(menubar);

    createFileMenu(menuActionLisener, menubar);
    createEditMenu(menuActionLisener, menubar);
    createHelpMenu(menuActionLisener, menubar);
  }

  private boolean isGerman() {
    return germanIso3Code.equalsIgnoreCase(defaultIso3Code);
  }


  private void createFileMenu(final MenuActionListener menuActionLisener, final JMenuBar menubar) {
    final JMenu fileMenu = new JMenu(menuLabels.getString("fileMenu"));
    menubar.add(fileMenu);
    fileMenu.setMnemonic(isGerman() ? 'D' : 'F');

    final JMenuItem close = new JMenuItem(menuLabels.getString("closeItem"));
    fileMenu.add(close);
    close.setActionCommand(MenuActions.CLOSE.name()); //NOPMD
    close.addActionListener(menuActionLisener);
    close.setMnemonic(isGerman() ? 'S' : 'C');
    close.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
  }

  private void createHelpMenu(final MenuActionListener menuActionLisener, final JMenuBar menubar) {
    final JMenu helpMenu = new JMenu(menuLabels.getString("helpMenu"));
    menubar.add(helpMenu);
    helpMenu.setMnemonic('H');

    final JMenuItem contents = new JMenuItem(menuLabels.getString("contents"));
    helpMenu.add(contents);
    contents.addActionListener(menuActionLisener);
    contents.setActionCommand(MenuActions.SHOW_HELP_CONTENTS.name()); //NOPMD
    contents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

    final JMenuItem report = new JMenuItem(menuLabels.getString("reportIssue"));
    helpMenu.add(report);
    report.addActionListener(menuActionLisener);
    report.setActionCommand(MenuActions.SHOW_BUGS_FEATURES.name()); //NOPMD
    report.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));

    helpMenu.addSeparator();

    final JMenuItem about = new JMenuItem(menuLabels.getString("aboutItem"));
    helpMenu.add(about);
    about.addActionListener(menuActionLisener);
    about.setActionCommand(MenuActions.SHOW_INFO.name()); //NOPMD
    about.setMnemonic(isGerman() ? 'Ü' : 'A');
  }

  private void createEditMenu(final MenuActionListener menuActionLisener, final JMenuBar menubar) {
    final JMenu edit = new JMenu(menuLabels.getString("editMenu"));
    menubar.add(edit);
    edit.setMnemonic(isGerman() ? 'B' : 'E');

    createCopyAndCut(menuActionLisener, edit);

    final JMenuItem clear = new JMenuItem(menuLabels.getString("clearLog"));
    edit.add(clear);
    clear.setActionCommand(MenuActions.CLEAR_LOG.name()); //NOPMD
    clear.addActionListener(menuActionLisener);
    clear.setMnemonic(isGerman() ? 'L' : 'C');
    clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

    edit.addSeparator();

    createViewLogLinesSubMenu(menuActionLisener, edit);
  }

  private void createViewLogLinesSubMenu(final MenuActionListener menuActionLisener,
                                         final JMenu edit) {
    final JMenu viewLogLines = new JMenu(menuLabels.getString("viewMaxLines"));
    edit.add(viewLogLines);
    final ButtonGroup logLinesGroup = new ButtonGroup();
    for (final MenuActions maxLogLines : MenuActions.logLines()) {
      final JRadioButtonMenuItem submenuItem = new JRadioButtonMenuItem("Maximum " //NOPMD
          + maxLogLines.getLines());
      viewLogLines.add(submenuItem);
      logLinesGroup.add(submenuItem);
      submenuItem.setSelected(maxLogLines.getLines() == view.getMaxLogLines());
      submenuItem.addActionListener(menuActionLisener);
      submenuItem.setActionCommand(maxLogLines.name());
    }
  }

  private void createCopyAndCut(final MenuActionListener menuActionLisener, final JMenu edit) {
    final JMenuItem copy = new JMenuItem(menuLabels.getString("copyLog"));
    edit.add(copy);
    copy.setActionCommand(MenuActions.COPY_LOG.name()); //NOPMD
    copy.addActionListener(menuActionLisener);
    copy.setMnemonic(isGerman() ? 'K' : 'C');
    copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

    final JMenuItem cut = new JMenuItem(menuLabels.getString("cutLog"));
    edit.add(cut);
    cut.setActionCommand(MenuActions.CUT_LOG.name()); //NOPMD
    cut.addActionListener(menuActionLisener);
    cut.setMnemonic(isGerman() ? 'O' : 'U');
    cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
  }

}
