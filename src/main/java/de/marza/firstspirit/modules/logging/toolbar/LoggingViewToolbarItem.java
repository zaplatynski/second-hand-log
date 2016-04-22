package de.marza.firstspirit.modules.logging.toolbar;

import de.espirit.firstspirit.client.plugin.toolbar.ExecutableToolbarItem;
import de.espirit.firstspirit.client.plugin.toolbar.ToolbarContext;
import de.marza.firstspirit.modules.logging.console.ConsoleWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


/**
 * The type Create experiment site architect toolbar item.
 */
public class LoggingViewToolbarItem implements ExecutableToolbarItem {

    public void execute(@NotNull final ToolbarContext context) {
        ConsoleWindow.getInstance().show();
    }

    public String getLabel(@NotNull final ToolbarContext context) {
        return "The Second-Hand Log";
    }

    public boolean isEnabled(@NotNull final ToolbarContext context) {
        return true;
    }

    public boolean isVisible(@NotNull final ToolbarContext context) {
        return true;
    }

    public Icon getIcon(@NotNull final ToolbarContext context) {
        return ConsoleWindow.getInstance().getIcon();
    }

    public Icon getPressedIcon(@NotNull final ToolbarContext context) {
        return ConsoleWindow.getInstance().getImageIconPressed();
    }

    public Icon getRollOverIcon(@NotNull final ToolbarContext context) {
        return ConsoleWindow.getInstance().getIcon();
    }
}
