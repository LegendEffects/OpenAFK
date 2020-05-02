package co.uk.legendeffects.openafk.handlers;

import co.uk.legendeffects.openafk.OpenAFK;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener {
    private final OpenAFK plugin;

    public OnChat(OpenAFK plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(AsyncPlayerChatEvent event) {
        if(!plugin.getConfig().getBoolean("detection.events.chatEvent")) {
            return;
        }

        plugin.setCheckAmount(event.getPlayer(), 0);
    }
}
