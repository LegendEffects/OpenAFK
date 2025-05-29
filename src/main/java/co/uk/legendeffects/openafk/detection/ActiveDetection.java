package co.uk.legendeffects.openafk.detection;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.ActionType;
import co.uk.legendeffects.openafk.util.DataHandler;
import co.uk.legendeffects.openafk.util.LocationHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ActiveDetection implements Listener {
    private final OpenAFK plugin;

    public ActiveDetection(OpenAFK plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(!plugin.getConfig().getBoolean("detection.events.blockBreakEvent")) {
            return;
        }

        plugin.setCheckAmount(event.getPlayer(), 0);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!plugin.getConfig().getBoolean("detection.events.blockPlaceEvent")) {
            return;
        }

        plugin.setCheckAmount(event.getPlayer(), 0);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(!plugin.getConfig().getBoolean("detection.events.chatEvent")) {
            return;
        }

        plugin.setCheckAmount(event.getPlayer(), 0);
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        DataHandler data = plugin.getPlayerData();

        if (data.playerHasData(player)) {
            player.teleport(LocationHelper.deserialize(data.getPlayer(player).getString("location")));
            data.deletePlayer(player);

            player.resetTitle();
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if (plugin.isAfkPlayer(event.getPlayer())) {
            plugin.getActionParser().parse(event.getPlayer(), ActionType.OTHER, "onAfkDisconnect");
        }

        plugin.forgetPlayer(event.getPlayer());
    }
}
