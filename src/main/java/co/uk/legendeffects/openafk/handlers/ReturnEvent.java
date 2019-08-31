package co.uk.legendeffects.openafk.handlers;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import co.uk.legendeffects.openafk.util.LocationHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReturnEvent implements Listener {

    OpenAFK plugin;
    public ReturnEvent(OpenAFK plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void Handler(PlayerReturnEvent event) {
        event.getPlayer().sendTitle("You are now AFK.", "See you soon!", 0, 2, 10);

        FileConfiguration data = plugin.getPlayerData().getPlayer(event.getPlayer());
        event.getPlayer().teleport(new LocationHelper().deserialize(data.getString("location")));

        event.getPlayer().sendMessage("Welcome Back!");
    }
}
