package de.marza.firstspirit.modules.logging.toolbar;

import de.espirit.firstspirit.client.plugin.toolbar.ExecutableToolbarItem;
import de.espirit.firstspirit.client.plugin.toolbar.ToolbarContext;
import de.marza.firstspirit.modules.logging.console.MessageConsole;

import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;


/**
 * The type Create experiment site architect toolbar item.
 */
public class LoggingViewToolbarItem implements ExecutableToolbarItem {

    private final ImageIcon icon;
    private final ImageIcon imageIconPressed;
    private JFrame window;

    public LoggingViewToolbarItem() {
        icon = new ImageIcon(this.getClass().getResource("/log-icon.png"));
        imageIconPressed = new ImageIcon(this.getClass().getResource("/log-icon_pressed.png"));
    }

    public void execute(@NotNull final ToolbarContext context) {
        if (window == null) {
            window = new JFrame("The Second-Hand Log");
            window.setIconImage(icon.getImage());
            window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            window.setPreferredSize(new Dimension(screenSize.width >> 1, screenSize.height >> 1));

            final JTextComponent textComponent = new JTextPane();

            textComponent.setFont(new Font("Courier New", Font.PLAIN, 12));

            window.getContentPane().add(new JScrollPane(textComponent));
            final MessageConsole mc = new MessageConsole(textComponent);
            mc.redirectOut(null, System.out);
            mc.redirectErr(Color.RED, System.err);
            mc.setMessageLines(1000);
            window.pack();
        }
        window.setVisible(true);
        window.requestFocus();
    }

    public String getLabel(@NotNull final ToolbarContext context) {
        return "Logging Console";
    }

    public boolean isEnabled(@NotNull final ToolbarContext context) {
        return true;
    }

    public boolean isVisible(@NotNull final ToolbarContext context) {
        return true;
    }

    public Icon getIcon(@NotNull final ToolbarContext context) {
        return icon;
    }

    public Icon getPressedIcon(@NotNull final ToolbarContext context) {
        return imageIconPressed;
    }

    public Icon getRollOverIcon(@NotNull final ToolbarContext context) {
        return icon;
    }
}
