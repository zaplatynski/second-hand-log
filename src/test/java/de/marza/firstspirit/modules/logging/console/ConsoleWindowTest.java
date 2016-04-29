package de.marza.firstspirit.modules.logging.console;

import javax.swing.*;

public class ConsoleWindowTest {

    public static void main(final String[] args) throws Exception {

        ConsoleWindow.getInstance().getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ConsoleWindow.getInstance().show();

        simulateLogEvents();
    }

    private static void simulateLogEvents() throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            System.out.println(i + ". test message");
            Thread.currentThread().sleep(75);
            System.err.println(++i + ". error");
            Thread.currentThread().sleep(500);
        }
    }
}
