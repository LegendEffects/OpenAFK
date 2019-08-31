package co.uk.legendeffects.openafk.handlers;

import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AfkEvent implements Listener {

    @EventHandler
    public void Handler(PlayerAfkEvent event) {
        Player player = event.getPlayer();

        player.sendTitle("You are now AFK.", "See you soon!", 10, 5000, 10);
        player.sendMessage("You are now AFK");
    }
}
