package co.uk.legendeffects.openafk;

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
}
