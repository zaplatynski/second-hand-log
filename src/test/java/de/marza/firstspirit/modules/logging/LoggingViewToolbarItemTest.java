package de.marza.firstspirit.modules.logging;

import de.marza.firstspirit.modules.logging.console.ConsoleWindow;
import de.marza.firstspirit.modules.logging.toolbar.LoggingViewToolbarItem;

import javax.swing.*;

public class LoggingViewToolbarItemTest {

    public static void main(final String[] args) throws Exception {
        final LoggingViewToolbarItem view = new LoggingViewToolbarItem();

        view.execute(null);

        ConsoleWindow.getInstance().getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        simulateLogEvents();
    }

    private static void simulateLogEvents() throws InterruptedException {
        for (int i = 0; i < 200; i++) {
            System.out.println(i + ". test message");
            Thread.currentThread().sleep(75);
            System.err.println(++i + ". error");
            Thread.currentThread().sleep(500);
        }
    }
}
