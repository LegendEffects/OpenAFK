package co.uk.legendeffects.openafk.handlers;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.util.LocationHelper;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AfkEvent implements Listener {

    private OpenAFK plugin;
    public AfkEvent(OpenAFK plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void Handler(PlayerAfkEvent event) {
        Player player = event.getPlayer();
        FileConfiguration data = plugin.getPlayerData().getPlayer(player);
        
        data.set("location", new LocationHelper().serialize(player.getLocation()));
        plugin.getPlayerData().savePlayer(player);

        player.sendTitle("You are now AFK.", "See you soon!", 10, 5000, 10);
        player.sendMessage("You are now AFK");
    }
}
