package de.marza.firstspirit.modules.logging.console;

import org.jetbrains.annotations.NotNull;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

/**
 * The type Console window.
 */
public final class ConsoleWindow {

  private static final ConsoleWindow SELF = new ConsoleWindow();
  private static final int MAX_LOG_LINES = MenuActions.SHOW_LOG_LINES_1K.getLines();
  private final ImageIcon icon;
  private final ImageIcon imageIconPressed;
  private final ResourceBundle menuLabels;
  private final String defaultIso3Code;
  private final String germanIso3Code;
  private final Dimension screenSize;
  private JFrame window;
  private MessageConsole console;
  private JTextComponent textComponent;

  private ConsoleWindow() {
    icon = new ImageIcon(this.getClass().getResource("/log-icon.png"));
    imageIconPressed = new ImageIcon(this.getClass().getResource("/log-icon_pressed.png"));
    menuLabels = ResourceBundle.getBundle("de.marza.firstspirit.modules.logging.MenuLabels");
    defaultIso3Code = Locale.getDefault().getISO3Language();
    germanIso3Code = Locale.GERMAN.getISO3Language();
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  @NotNull
  public static ConsoleWindow getInstance() {
    return SELF;
  }

  private void createViewLogLinesSubMenu(final MenuActionListener menuActionLisener,
                                         final JMenu edit) {
    final JMenu viewLogLines = new JMenu(menuLabels.getString("viewMaxLines"));
    edit.add(viewLogLines);
    final ButtonGroup logLinesGroup = new ButtonGroup();
    for (final MenuActions maxLogLines : MenuActions.logLines()) {
      final JRadioButtonMenuItem submenuItem = new JRadioButtonMenuItem("Maximum "
          + maxLogLines.getLines());
      viewLogLines.add(submenuItem);
      logLinesGroup.add(submenuItem);
      submenuItem.setSelected(maxLogLines.getLines() == MAX_LOG_LINES);
      submenuItem.addActionListener(menuActionLisener);
      submenuItem.setActionCommand(maxLogLines.name());
    }
  }

  private boolean isGerman() {
    return germanIso3Code.equalsIgnoreCase(defaultIso3Code);
  }

  /**
   * Gets frame.
   *
   * @param projectName the FirstSpirit project name
   * @return the frame
   */
  @NotNull
  public JFrame getFrame(final String projectName) {
    if (window == null) {
      window = setupFrame(projectName);

      setupMenu();

      textComponent = setupLogView();

      setupConsole();

      window.pack();
    }
    return window;
  }

  @NotNull
  private JFrame setupFrame(final String projectName) {
    final String appName = createAppName(projectName);
    final JFrame frame = new JFrame(appName);
    frame.setIconImage(icon.getImage());
    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    //Dynamic size
    final Dimension preferredSize = new Dimension(screenSize.width >> 1, screenSize.height >> 1);
    frame.setPreferredSize(preferredSize);

    return frame;
  }

  @NotNull
  private String createAppName(final String projectName) {
    String appName = menuLabels.getString("appName");
    if (projectName != null) {
      appName += " / " + projectName;
    }
    return appName;
  }

  private void setupMenu() {
    final MenuActionListener menuActionLisener = new MenuActionListener();

    final JMenuBar menubar = new JMenuBar();
    window.setJMenuBar(menubar);

    createFileMenu(menuActionLisener, menubar);
    createEditMenu(menuActionLisener, menubar);
    createHelpMenu(menuActionLisener, menubar);
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

  @NotNull
  private JTextComponent setupLogView() {
    final JTextComponent textPane = new JTextPane();
    textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    window.getContentPane().add(new JScrollPane(textPane));
    return textPane;
  }

  private void setupConsole() {
    console = new MessageConsole(textComponent);
    console.redirectErr(System.err);
    console.redirectOut(System.out);
    console.setMaxMessageLines(MAX_LOG_LINES);
  }

  /**
   * Show window.
   *
   * @param projectName the project name
   */
  public void show(final String projectName) {
    getFrame(projectName);
    window.setVisible(true);
    window.requestFocus();
  }

  /**
   * Gets console.
   *
   * @return the console
   */
  @NotNull
  public MessageConsole getConsole() {
    getFrame(null);
    return console;
  }

  /**
   * Gets icon.
   *
   * @return the icon
   */
  @NotNull
  public ImageIcon getIcon() {
    return icon;
  }

  /**
   * Gets image icon pressed.
   *
   * @return the image icon pressed
   */
  @NotNull
  public ImageIcon getImageIconPressed() {
    return imageIconPressed;
  }

  /**
   * Gets menu labels.
   *
   * @return the menu labels
   */
  @NotNull
  public ResourceBundle getMenuLabels() {
    return menuLabels;
  }

  /**
   * Clear log.
   */
  public void clearLog() {
    textComponent.setText("");
  }

  /**
   * Gets log messages.
   *
   * @return the log messages
   */
  public String getLogMessages() {
    return textComponent.getText();
  }
}
