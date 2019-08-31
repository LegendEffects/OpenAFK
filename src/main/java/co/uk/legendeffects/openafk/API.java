package co.uk.legendeffects.openafk;

import org.bukkit.entity.Player;

import java.util.Set;

public class API {
    public boolean isAfk(Player player) {
        return OpenAFK.getInstance().afkPlayers.contains(player);
    }

    public Set<Player> getAfkSet() {
        return OpenAFK.getInstance().afkPlayers;
    }
}
