package co.uk.legendeffects.openafk.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Broadcast implements ActionExecutor {
    @Override
    public boolean isEnabled(OpenAFK plugin) {
        return plugin.getConfig().getBoolean("actions.broadcast.enabled");
    }

    @Override
    public void onAfk(PlayerAfkEvent event, OpenAFK plugin) {
        FileConfiguration config = plugin.getConfig();
        if(config.getBoolean("actions.broadcast.onAfk")) {
            this.announce(OpenAFK.parse(event.getPlayer(), config.getString("actions.broadcast.message")), config.getBoolean("actions.broadcast.needPermission"));
        }
    }

    @Override
    public void onReturn(PlayerReturnEvent event, OpenAFK plugin) {
        FileConfiguration config = plugin.getConfig();
        if(config.getBoolean("actions.broadcast.onReturn")) {
            this.announce(OpenAFK.parse(event.getPlayer(), config.getString("actions.broadcast.returnMessage")), config.getBoolean("actions.broadcast.needPermission"));
        }
    }

    private void announce(String message, boolean needPermission) {
        if(needPermission) {
            Bukkit.getServer().broadcast(message, "openafk.announce");
        } else {
            Bukkit.getServer().broadcastMessage(message);
        }
    }
}
