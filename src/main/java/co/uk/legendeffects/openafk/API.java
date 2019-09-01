package co.uk.legendeffects.openafk;

import co.uk.legendeffects.openafk.actions.ActionExecutor;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

public class API {
    public boolean isAfk(Player player) {
        return OpenAFK.getPlugin().afkPlayers.contains(player);
    }

    public Set<Player> getAfkSet() {
        return OpenAFK.getPlugin().afkPlayers;
    }

    public void overrideLastLocation(Player player, Location newLocation) { OpenAFK.getPlugin().lastLocations.replace(player, newLocation); }

    public void addActionExecutor(ActionExecutor action) {
        OpenAFK.getPlugin().getActionOrchestrator().registerAction(action);
    }
}
