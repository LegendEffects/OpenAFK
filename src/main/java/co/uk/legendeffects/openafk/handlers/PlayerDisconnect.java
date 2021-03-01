package co.uk.legendeffects.openafk.handlers;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnect implements Listener {
    private final OpenAFK openAFK;

    public PlayerDisconnect(OpenAFK openAFK) {
        this.openAFK = openAFK;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if(this.openAFK.isAfkPlayer(event.getPlayer())) {
            openAFK.getActionParser().parse(event.getPlayer(), ActionType.OTHER, "onAfkDisconnect");
        }

        this.openAFK.getPlayerData().unloadPlayer(event.getPlayer());
        this.openAFK.removeAfkPlayerFromCache(event.getPlayer());
    }
}
