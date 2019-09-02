package co.uk.legendeffects.openafk.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import org.bukkit.configuration.file.FileConfiguration;

public class Title implements ActionExecutor {
    @Override
    public boolean isEnabled(OpenAFK plugin) {
        return plugin.getConfig().getBoolean("actions.title.enabled");
    }

    @Override
    public void onAfk(PlayerAfkEvent event, OpenAFK plugin) {
        FileConfiguration config = plugin.getConfig();

        final String title = OpenAFK.parse(event.getPlayer(), config.getString("actions.title.message.title"));
        final String subtitle = OpenAFK.parse(event.getPlayer(), config.getString("actions.title.message.subtitle"));

        event.getPlayer().sendTitle(title, subtitle, config.getInt("actions.title.message.fadeIn"), 999999999, config.getInt("actions.title.message.fadeOut"));
    }

    @Override
    public void onReturn(PlayerReturnEvent event, OpenAFK plugin) {
        FileConfiguration config = plugin.getConfig();

        final String title = OpenAFK.parse(event.getPlayer(), config.getString("actions.title.returnMessage.title"));
        final String subtitle = OpenAFK.parse(event.getPlayer(), config.getString("actions.title.returnMessage.subtitle"));

        event.getPlayer().sendTitle(title, subtitle, config.getInt("actions.title.returnMessage.fadeIn"), config.getInt("actions.title.returnMessage.length"), config.getInt("actions.title.returnMessage.fadeOut"));
    }
}
