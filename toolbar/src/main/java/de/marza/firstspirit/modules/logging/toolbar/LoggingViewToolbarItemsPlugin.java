package de.marza.firstspirit.modules.logging.toolbar;

import de.espirit.firstspirit.access.BaseContext;
import de.espirit.firstspirit.client.plugin.JavaClientEditorialToolbarItemsPlugin;
import de.espirit.firstspirit.client.plugin.toolbar.ExecutableToolbarItem;
import de.espirit.firstspirit.client.plugin.toolbar.JavaClientToolbarItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Provide JavaClient toolbar items. These will be displayed in the toolbar section above the client
 * window's workspace area.
 */
public final class LoggingViewToolbarItemsPlugin implements JavaClientEditorialToolbarItemsPlugin {

  /**
   * List of toolbar items.
   */
  private final List<ExecutableToolbarItem> items;

  /**
   * Constructor.
   */
  public LoggingViewToolbarItemsPlugin() {
    this.items = new ArrayList<>();
  }

  @Override
  public Collection<? extends JavaClientToolbarItem> getItems() {
    return Collections.unmodifiableCollection(items);
  }

  @Override
  public void setUp(@NotNull final BaseContext context) {
    items.add(new LoggingViewToolbarItem());
  }

  @Override
  public void tearDown() {
    // Do nothing.
  }
}
