package de.marza.firstspirit.modules.logging.console;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;


public class MenuController implements Runnable {

    private final ActionEvent event;

    public MenuController(final ActionEvent event) {
        this.event = event;
    }

    @Override
    public void run() {
        final ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
        switch (MenuActions.valueOf(event.getActionCommand())) {
            case CLOSE:
                consoleWindow.getFrame().setVisible(false);
                break;
            case SHOW_INFO:
                final JFrame parent = consoleWindow.getFrame();
                final String title = consoleWindow.getMenuLabels().getString("aboutItem");
                JOptionPane.showMessageDialog(parent, readAboutText(), title, JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    @Nullable
    private JEditorPane readAboutText() {
        JEditorPane message = null;


        try (final BufferedReader txtReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/about.txt")))) {
            String line;
            final StringBuilder sb = new StringBuilder();
            while ((line = txtReader.readLine()) != null) {
                sb.append(line);
            }
            message = new JEditorPane("text/html", sb.toString());
            message.setEditable(false);
            final Color backgrounfdColor = UIManager.getColor("Panel.background");
            message.setBackground(backgrounfdColor);
            message.addHyperlinkListener(new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(final HyperlinkEvent e) {
                    if (Desktop.isDesktopSupported() && e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (final IOException | URISyntaxException exception) {
                            System.err.println("An error occurred while reading the about text:" + exception.toString());
                            exception.printStackTrace(System.err);
                        }
                    }
                }
            });
        } catch (final IOException exception) {
            System.err.println("An error occurred while reading the about text:" + exception.toString());
            exception.printStackTrace(System.err);
        }
        return message;
    }
}
