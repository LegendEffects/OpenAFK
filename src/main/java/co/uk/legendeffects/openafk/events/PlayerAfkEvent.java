package co.uk.legendeffects.openafk.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAfkEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private Player player;

    public PlayerAfkEvent(boolean async, Player player) {
        super(async);
        this.player = player;
    }


    public Player getPlayer() {
        return player;
    }

    // DEFAULT
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
