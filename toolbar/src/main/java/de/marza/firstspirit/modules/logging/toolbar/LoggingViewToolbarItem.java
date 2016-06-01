package de.marza.firstspirit.modules.logging.toolbar;

import de.espirit.firstspirit.agency.ProjectAgent;
import de.espirit.firstspirit.client.plugin.toolbar.ExecutableToolbarItem;
import de.espirit.firstspirit.client.plugin.toolbar.ToolbarContext;
import de.marza.firstspirit.modules.logging.console.ConsoleWindow;

import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

import javax.swing.Icon;


/**
 * The type Create experiment site architect toolbar item.
 */
public final class LoggingViewToolbarItem implements ExecutableToolbarItem {

  private final ResourceBundle menuLabels;
  private final ConsoleWindow consoleWindow;

  /**
   * Instantiates a new Logging view toolbar item.
   */
  public LoggingViewToolbarItem() {
    consoleWindow = ConsoleWindow.getInstance();
    menuLabels = consoleWindow.getMenuLabels();
  }

  @Override
  public void execute(@NotNull final ToolbarContext context) {
    final ProjectAgent projectAgent = context.requireSpecialist(ProjectAgent.TYPE);
    final String projectName = projectAgent.getName(); //NOPMD
    consoleWindow.show(projectName);
  }

  @Override
  public String getLabel(@NotNull final ToolbarContext context) {
    return menuLabels.getString("appName");
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
    return consoleWindow.getIcon();
  }

  @Override
  public Icon getPressedIcon(@NotNull final ToolbarContext context) {
    return consoleWindow.getImageIconPressed();
  }

  @Override
  public Icon getRollOverIcon(@NotNull final ToolbarContext context) {
    return consoleWindow.getIcon();
  }
}
