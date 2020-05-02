package co.uk.legendeffects.openafk.handlers;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.util.DataHandler;
import co.uk.legendeffects.openafk.util.LocationHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnect implements Listener {

    final private OpenAFK plugin;
    public PlayerConnect(OpenAFK plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void handler(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        DataHandler data = plugin.getPlayerData();

        if(data.playerHasData(player)) {
            player.teleport(LocationHelper.deserialize(data.getPlayer(player).getString("location")));
            data.deletePlayer(player);

            player.resetTitle();
        }

    }
}
