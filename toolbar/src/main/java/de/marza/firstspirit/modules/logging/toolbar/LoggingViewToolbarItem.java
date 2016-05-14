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
public class LoggingViewToolbarItem implements ExecutableToolbarItem {

  private final ResourceBundle menuLabels;

  /**
   * Instantiates a new Logging view toolbar item.
   */
  public LoggingViewToolbarItem() {
    this.menuLabels = ResourceBundle.getBundle(
        "de.marza.firstspirit.modules.logging.MenuLabels");
  }

  @Override
  public void execute(@NotNull final ToolbarContext context) {
    final ProjectAgent projectAgent = context.requireSpecialist(ProjectAgent.TYPE);
    ConsoleWindow.getInstance().show(projectAgent.getName());
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
