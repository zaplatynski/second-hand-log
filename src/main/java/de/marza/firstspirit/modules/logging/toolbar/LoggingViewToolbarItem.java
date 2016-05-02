package de.marza.firstspirit.modules.logging.toolbar;

import de.espirit.firstspirit.client.plugin.toolbar.ExecutableToolbarItem;
import de.espirit.firstspirit.client.plugin.toolbar.ToolbarContext;
import de.marza.firstspirit.modules.logging.console.ConsoleWindow;

import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;


/**
 * The type Create experiment site architect toolbar item.
 */
public class LoggingViewToolbarItem implements ExecutableToolbarItem {

  @Override
  public void execute(@NotNull final ToolbarContext context) {
    ConsoleWindow.getInstance().show();
  }

  @Override
  public String getLabel(@NotNull final ToolbarContext context) {
    return "The Second-Hand Log";
  }

  @Override
  public boolean isEnabled(@NotNull final ToolbarContext context) {
    return true;
  }

  @Override
  public boolean isVisible(@NotNull final ToolbarContext context) {
    return true;
  }

  @Override
  public Icon getIcon(@NotNull final ToolbarContext context) {
    return ConsoleWindow.getInstance().getIcon();
  }

  @Override
  public Icon getPressedIcon(@NotNull final ToolbarContext context) {
    return ConsoleWindow.getInstance().getImageIconPressed();
  }

  @Override
  public Icon getRollOverIcon(@NotNull final ToolbarContext context) {
    return ConsoleWindow.getInstance().getIcon();
  }
}
