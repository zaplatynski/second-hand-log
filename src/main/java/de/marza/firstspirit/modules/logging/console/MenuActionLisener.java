package de.marza.firstspirit.modules.logging.console;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuActionLisener implements ActionListener {

    @Override
    public void actionPerformed(final ActionEvent event) {
        SwingUtilities.invokeLater(new MenuController(event));
    }
}
