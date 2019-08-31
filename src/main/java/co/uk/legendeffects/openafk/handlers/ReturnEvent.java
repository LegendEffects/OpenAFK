package co.uk.legendeffects.openafk.handlers;

import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReturnEvent implements Listener {

    @EventHandler
    public void Handler(PlayerReturnEvent event) {
        event.getPlayer().sendTitle("You are now AFK.", "See you soon!", 0, 2, 10);
        event.getPlayer().sendMessage("Welcome Back!");
    }
}
