package de.marza.firstspirit.modules.logging.console;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;


public class MenuController implements Runnable {

    private final ActionEvent event;

    public MenuController(final ActionEvent event) {
        this.event = event;
    }

    @Override
    public void run() {
        final ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
        final JFrame parent = consoleWindow.getFrame();
        switch (MenuActions.valueOf(event.getActionCommand())) {
            case CLOSE:
                parent.setVisible(false);
                break;
            case SHOW_INFO:
                final String title = consoleWindow.getMenuLabels().getString("aboutItem");
                JOptionPane.showMessageDialog(parent, readAboutText(), title, JOptionPane.PLAIN_MESSAGE);
                break;
            case SHOW_BUGS_FEATURES:
                HyperlinkExecutor.browseTo(URI.create("https://github.com/zaplatynski/second-hand-log/issues"));
                break;
            case SHOW_HELP_CONTENTS:
                HyperlinkExecutor.browseTo(URI.create("https://github.com/zaplatynski/second-hand-log/wiki"));
                break;
            default:
                final String errorMessage = consoleWindow.getMenuLabels().getString("missing.menu.action");
                final String errorTitle = consoleWindow.getMenuLabels().getString("unkown.menu.command");
                JOptionPane.showMessageDialog(parent, errorMessage, errorTitle, JOptionPane.ERROR_MESSAGE);
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
            message.addHyperlinkListener(new HyperlinkExecutor());
        } catch (final IOException exception) {
            System.err.println("An error occurred while reading the about text:" + exception.toString());
            exception.printStackTrace(System.err);
        }
        return message;
    }

}
