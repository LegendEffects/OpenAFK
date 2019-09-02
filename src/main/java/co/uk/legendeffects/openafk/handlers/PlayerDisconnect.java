package co.uk.legendeffects.openafk.handlers;

import co.uk.legendeffects.openafk.OpenAFK;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnect implements Listener {

    final private OpenAFK openAFK;
    public PlayerDisconnect(OpenAFK openAFK) {
        this.openAFK = openAFK;
    }

    @EventHandler
    public void Handler(PlayerQuitEvent event) {
        this.openAFK.getPlayerData().unloadPlayer(event.getPlayer());
        this.openAFK.removeAfkPlayer(event.getPlayer());
    }
}
