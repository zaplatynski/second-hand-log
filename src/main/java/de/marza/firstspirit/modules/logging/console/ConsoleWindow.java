package de.marza.firstspirit.modules.logging.console;

import org.jetbrains.annotations.NotNull;

import java.awt.Color;
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
public class ConsoleWindow {

  private static final ConsoleWindow SELF = new ConsoleWindow();
  private static final int MAX_LOG_LINES = MenuActions.SHOW_LOG_LINES_1K.getLines();
  private final ImageIcon icon;
  private final ImageIcon imageIconPressed;
  private final ResourceBundle menuLabels;
  private JFrame window;
  private MessageConsole console;
  private JTextComponent textComponent;

  private ConsoleWindow() {
    icon = new ImageIcon(this.getClass().getResource("/log-icon.png"));
    imageIconPressed = new ImageIcon(this.getClass().getResource("/log-icon_pressed.png"));
    menuLabels = ResourceBundle.getBundle("de.marza.firstspirit.modules.logging.MenuLabels");
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

  private static boolean isGerman() {
    return Locale.GERMAN.getISO3Language().equalsIgnoreCase(Locale.getDefault().getISO3Language());
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
    final JFrame frame = new JFrame(menuLabels.getString("appName")
        + (projectName != null ? " / " + projectName : ""));
    frame.setIconImage(icon.getImage());
    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    //Dynamic size
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setPreferredSize(new Dimension(screenSize.width >> 1, screenSize.height >> 1));

    return frame;
  }

  private void setupMenu() {
    final MenuActionListener menuActionLisener = new MenuActionListener();

    final JMenuBar menubar = new JMenuBar();
    window.setJMenuBar(menubar);

    final JMenu fileMenu = new JMenu(menuLabels.getString("fileMenu"));
    menubar.add(fileMenu);
    fileMenu.setMnemonic(isGerman() ? 'D' : 'F');

    final JMenuItem close = new JMenuItem(menuLabels.getString("closeItem"));
    fileMenu.add(close);
    close.setActionCommand(MenuActions.CLOSE.name());
    close.addActionListener(menuActionLisener);
    close.setMnemonic(isGerman() ? 'S' : 'C');
    close.setAccelerator(KeyStroke.getKeyStroke("alt F4"));

    final JMenu edit = new JMenu(menuLabels.getString("editMenu"));
    menubar.add(edit);
    edit.setMnemonic(isGerman() ? 'B' : 'E');

    final JMenuItem copy = new JMenuItem(menuLabels.getString("copyLog"));
    edit.add(copy);
    copy.setActionCommand(MenuActions.COPY_LOG.name());
    copy.addActionListener(menuActionLisener);
    copy.setMnemonic(isGerman() ? 'K' : 'C');
    copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

    final JMenuItem cut = new JMenuItem(menuLabels.getString("cutLog"));
    edit.add(cut);
    cut.setActionCommand(MenuActions.CUT_LOG.name());
    cut.addActionListener(menuActionLisener);
    cut.setMnemonic(isGerman() ? 'O' : 'U');
    cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

    final JMenuItem clear = new JMenuItem(menuLabels.getString("clearLog"));
    edit.add(clear);
    clear.setActionCommand(MenuActions.CLEAR_LOG.name());
    clear.addActionListener(menuActionLisener);
    clear.setMnemonic(isGerman() ? 'L' : 'C');
    clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

    edit.addSeparator();

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


    final JMenu helpMenu = new JMenu(menuLabels.getString("helpMenu"));
    menubar.add(helpMenu);
    helpMenu.setMnemonic('H');

    final JMenuItem contents = new JMenuItem(menuLabels.getString("contents"));
    helpMenu.add(contents);
    contents.addActionListener(menuActionLisener);
    contents.setActionCommand(MenuActions.SHOW_HELP_CONTENTS.name());
    contents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

    final JMenuItem report = new JMenuItem(menuLabels.getString("reportIssue"));
    helpMenu.add(report);
    report.addActionListener(menuActionLisener);
    report.setActionCommand(MenuActions.SHOW_BUGS_FEATURES.name());
    report.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));

    helpMenu.addSeparator();

    final JMenuItem about = new JMenuItem(menuLabels.getString("aboutItem"));
    helpMenu.add(about);
    about.addActionListener(menuActionLisener);
    about.setActionCommand(MenuActions.SHOW_INFO.name());
    about.setMnemonic(isGerman() ? 'Ü' : 'A');


  }

  @NotNull
  private JTextComponent setupLogView() {
    final JTextComponent textComponent = new JTextPane();
    textComponent.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    window.getContentPane().add(new JScrollPane(textComponent));
    return textComponent;
  }

  private void setupConsole() {
    console = new MessageConsole(textComponent);
    console.redirectOut(null, System.out);
    console.redirectErr(Color.RED, System.err);
    console.setMaxMessageLines(MAX_LOG_LINES);
  }

  /**
   * Show.
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
