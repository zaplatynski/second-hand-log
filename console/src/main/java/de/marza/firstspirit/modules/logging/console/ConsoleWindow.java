package de.marza.firstspirit.modules.logging.console;

import org.jetbrains.annotations.NotNull;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.text.JTextComponent;

/**
 * The type Console window.
 */
public final class ConsoleWindow {


  private static final ConsoleWindow SELF = new ConsoleWindow();

  private final ImageIcon icon;
  private final ImageIcon imageIconPressed;
  private final ResourceBundle menuLabels;
  private final Dimension screenSize;
  private final ConsoleMenu consoleMenu;
  private final ConsoleView consoleView;
  private JFrame window;
  private MessageConsole console;
  private JTextComponent textComponent;

  private ConsoleWindow() {
    icon = new ImageIcon(this.getClass().getResource("/log-icon.png"));
    imageIconPressed = new ImageIcon(this.getClass().getResource("/log-icon_pressed.png"));
    menuLabels = ResourceBundle.getBundle("de.marza.firstspirit.modules.logging.MenuLabels");
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    consoleView = new ConsoleView(MenuActions.SHOW_LOG_LINES_1K.getLines());
    consoleMenu = new ConsoleMenu(consoleView);
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
      consoleMenu.setupMenu(window);
      final Container contentPane = window.getContentPane();
      consoleView.init(contentPane);
      textComponent = consoleView.getTextPane();
      console = consoleView.getConsole();
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
