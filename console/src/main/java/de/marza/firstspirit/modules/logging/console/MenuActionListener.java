package de.marza.firstspirit.modules.logging.console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;


/**
 * The type Menu action listener.
 */
public class MenuActionListener implements ActionListener {

  @Override
  public void actionPerformed(final ActionEvent event) {
    SwingUtilities.invokeLater(new MenuController(event));
  }
}
