package de.marza.firstspirit.modules.logging.console;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class ConsoleWindow {

    private static final ConsoleWindow SELF = new ConsoleWindow();
    private final ImageIcon icon;
    private final ImageIcon imageIconPressed;
    private JFrame window;

    public static ConsoleWindow getInstance() {
        return SELF;
    }

    private ConsoleWindow() {
        icon = new ImageIcon(this.getClass().getResource("/log-icon.png"));
        imageIconPressed = new ImageIcon(this.getClass().getResource("/log-icon_pressed.png"));
    }

    public ConsoleWindow create() {
        if (window == null) {
            window = new JFrame("The Second-Hand Log");
            window.setIconImage(icon.getImage());
            window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


            //Dynamic size
            final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            window.setPreferredSize(new Dimension(screenSize.width >> 1, screenSize.height >> 1));

            final JTextComponent textComponent = new JTextPane();
            textComponent.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

            window.getContentPane().add(new JScrollPane(textComponent));
            final MessageConsole mc = new MessageConsole(textComponent);

            mc.redirectOut(null, System.out);
            mc.redirectErr(Color.RED, System.err);
            mc.setMessageLines(8000);

            window.pack();
        }
        return this;
    }

    public void show() {
        create();
        window.setVisible(true);
        window.requestFocus();
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public ImageIcon getImageIconPressed() {
        return imageIconPressed;
    }
}
