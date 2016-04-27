package de.marza.firstspirit.modules.logging.console;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsoleWindow {

    private static final ConsoleWindow SELF = new ConsoleWindow();
    private final ImageIcon icon;
    private final ImageIcon imageIconPressed;
    private final ResourceBundle menuLabels;
    private JFrame window;
    private MessageConsole console;
    private JTextComponent textComponent;

    @NotNull
    public static ConsoleWindow getInstance() {
        return SELF;
    }

    private ConsoleWindow() {
        icon = new ImageIcon(this.getClass().getResource("/log-icon.png"));
        imageIconPressed = new ImageIcon(this.getClass().getResource("/log-icon_pressed.png"));
        menuLabels = ResourceBundle.getBundle("de.marza.firstspirit.modules.logging.MenuLabels");
    }

    @NotNull
    public JFrame getFrame() {
        if (window == null) {
            window = setupFrame();

            setupMenu();

            textComponent = setupUI();

            setupConsole();

            window.pack();
        }
        return window;
    }

    @NotNull
    private JFrame setupFrame() {
        final JFrame frame = new JFrame(menuLabels.getString("appName"));
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //Dynamic size
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(screenSize.width >> 1, screenSize.height >> 1));

        return frame;
    }

    private void setupMenu() {
        final MenuActionLisener menuActionLisener = new MenuActionLisener();

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

        final JMenuItem clear = new JMenuItem(menuLabels.getString("clearLog"));
        edit.add(clear);
        clear.setActionCommand(MenuActions.CLEAR_LOG.name());
        clear.addActionListener(menuActionLisener);
        clear.setMnemonic(isGerman() ? 'L' : 'C');
        clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

        final JMenu helpMenu = new JMenu(menuLabels.getString("helpMenu"));
        menubar.add(helpMenu);
        helpMenu.setMnemonic('H');

        final JMenuItem contents = new JMenuItem(menuLabels.getString("contents"));
        helpMenu.add(contents);
        contents.addActionListener(menuActionLisener);
        contents.setActionCommand(MenuActions.SHOW_HELP_CONTENTS.name());
        contents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

        final JMenuItem report = new JMenuItem(menuLabels.getString("report.a.bug.or.feature"));
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

    private static boolean isGerman() {
        return Locale.GERMAN.getISO3Language().equalsIgnoreCase(Locale.getDefault().getISO3Language());
    }

    @NotNull
    private JTextComponent setupUI() {
        final JTextComponent textComponent = new JTextPane();
        textComponent.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        window.getContentPane().add(new JScrollPane(textComponent));
        return textComponent;
    }

    private void setupConsole() {
        console = new MessageConsole(textComponent);
        console.redirectOut(null, System.out);
        console.redirectErr(Color.RED, System.err);
        console.setMessageLines(8000);
    }

    public void show() {
        getFrame();
        window.setVisible(true);
        window.requestFocus();
    }

    @NotNull
    public MessageConsole getConsole() {
        getFrame();
        return console;
    }

    @NotNull
    public ImageIcon getIcon() {
        return icon;
    }

    @NotNull
    public ImageIcon getImageIconPressed() {
        return imageIconPressed;
    }

    @NotNull
    public ResourceBundle getMenuLabels() {
        return menuLabels;
    }

    public void clearLog() {
        textComponent.setText("");
    }
}
