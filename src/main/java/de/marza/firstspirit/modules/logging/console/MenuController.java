package de.marza.firstspirit.modules.logging.console;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;


public class MenuController implements Runnable {

    private final ActionEvent event;
    private JEditorPane message;

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
            case COPY_LOG:
                final String logMessages = consoleWindow.getLogMessages();
                copyToClipboard(logMessages);
                break;
            case CLEAR_LOG:
                consoleWindow.clearLog();
                break;
            case SHOW_INFO:
                final String title = consoleWindow.getMenuLabels().getString("aboutItem");
                JOptionPane.showMessageDialog(parent, readAboutText(), title, JOptionPane.PLAIN_MESSAGE);
                break;
            case SHOW_BUGS_FEATURES:
                final URI issues = URI.create("https://github.com/zaplatynski/second-hand-log/issues");
                HyperlinkExecutor.browseTo(issues);
                break;
            case SHOW_HELP_CONTENTS:
                final URI wiki = URI.create("https://github.com/zaplatynski/second-hand-log/wiki");
                HyperlinkExecutor.browseTo(wiki);
                break;
            default:
                final String errorMessage = consoleWindow.getMenuLabels().getString("missingMenuAction");
                final String errorTitle = consoleWindow.getMenuLabels().getString("unkownMenuCommand");
                JOptionPane.showMessageDialog(parent, errorMessage, errorTitle, JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void copyToClipboard(final String logMessages) {
        final StringSelection stringSelection = new StringSelection(logMessages);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, LogClipboardOnwer.getInstance());
    }

    @Nullable
    private JEditorPane readAboutText() {
        if (message == null) {
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
        }
        return message;
    }

}
