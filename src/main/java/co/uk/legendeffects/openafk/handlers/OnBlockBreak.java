package co.uk.legendeffects.openafk.handlers;

import co.uk.legendeffects.openafk.OpenAFK;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreak implements Listener {
    private final OpenAFK plugin;

    public OnBlockBreak(OpenAFK plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(!plugin.getConfig().getBoolean("detection.events.blockBreakEvent")) {
            return;
        }

        plugin.setCheckAmount(event.getPlayer(), 0);
    }
}
