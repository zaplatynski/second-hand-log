package de.marza.firstspirit.modules.logging.console;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ResourceBundle;

public class ConsoleWindow {

    private static final ConsoleWindow SELF = new ConsoleWindow();
    private final ImageIcon icon;
    private final ImageIcon imageIconPressed;
    private final ResourceBundle menuLabels;
    private JFrame window;
    private MessageConsole console;

    public static ConsoleWindow getInstance() {
        return SELF;
    }

    private ConsoleWindow() {
        icon = new ImageIcon(this.getClass().getResource("/log-icon.png"));
        imageIconPressed = new ImageIcon(this.getClass().getResource("/log-icon_pressed.png"));
        menuLabels = ResourceBundle.getBundle("de.marza.firstspirit.modules.logging.MenuLabels");
    }

    public JFrame getFrame() {
        if (window == null) {
            window = setupFrame();

            setupMenu();

            final JTextComponent textComponent = setupUI();

            setupConsole(textComponent);

            window.pack();
        }
        return window;
    }

    private JFrame setupFrame() {
        final JFrame frame = new JFrame("The Second-Hand Log");
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

        final JMenuItem close = new JMenuItem(menuLabels.getString("closeItem"));
        fileMenu.add(close);
        close.setActionCommand(MenuActions.CLOSE.name());
        close.addActionListener(menuActionLisener);

        final JMenu helpMenu = new JMenu(menuLabels.getString("helpMenu"));
        menubar.add(helpMenu);

        final JMenuItem about = new JMenuItem(menuLabels.getString("aboutItem"));
        helpMenu.add(about);
        about.addActionListener(menuActionLisener);
        about.setActionCommand(MenuActions.SHOW_INFO.name());


    }

    @NotNull
    private JTextComponent setupUI() {
        final JTextComponent textComponent = new JTextPane();
        textComponent.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        window.getContentPane().add(new JScrollPane(textComponent));
        return textComponent;
    }

    private void setupConsole(final JTextComponent textComponent) {
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

    public MessageConsole getConsole() {
        getFrame();
        return console;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public ImageIcon getImageIconPressed() {
        return imageIconPressed;
    }

    public ResourceBundle getMenuLabels() {
        return menuLabels;
    }
}
