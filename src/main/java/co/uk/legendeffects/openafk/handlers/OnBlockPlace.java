package co.uk.legendeffects.openafk.handlers;

import co.uk.legendeffects.openafk.OpenAFK;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnBlockPlace implements Listener {
    private final OpenAFK plugin;

    public OnBlockPlace(OpenAFK plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent event) {
        if(!plugin.getConfig().getBoolean("detection.events.blockPlaceEvent")) {
            return;
        }

        plugin.setCheckAmount(event.getPlayer(), 0);
    }
}
