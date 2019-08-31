package co.uk.legendeffects.openafk.util;

import co.uk.legendeffects.openafk.Core;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CheckTask extends BukkitRunnable {
    private HashMap<Player, Location> lastLocations = new HashMap<>();
    private HashMap<Player, Integer> checkAmounts = new HashMap<>();

    private Set<Player> isAfk = new HashSet<>();

    private final Core core;

    public CheckTask(Core core) {
        this.core = core;
    }

    private boolean movedEnough(Player player) {
        if(!lastLocations.containsKey(player)) return true;
        return lastLocations.get(player).distance(player.getLocation()) > this.core.getConfig("config.yml").getInt("movementDistance");
    }

    private void sendOutAfk(Player player) {
        if(!this.isAfk.contains(player)) {
            this.isAfk.add(player);

            PlayerAfkEvent event = new PlayerAfkEvent(true, player);
            this.core.getServer().getPluginManager().callEvent(event);
        }
    }

    private void sendOutNonAfk(Player player) {
        if(this.isAfk.contains(player)) {
            PlayerReturnEvent event = new PlayerReturnEvent(true, player);
            this.core.getServer().getPluginManager().callEvent(event);

            this.isAfk.remove(player);
            this.checkAmounts.remove(player);
        }

    }

    @Override
    public void run() {
        this.core.getServer().getOnlinePlayers().forEach(player -> {
            if(!this.movedEnough(player)) {
                if(checkAmounts.containsKey(player)) {
                    int currentAmount = checkAmounts.get(player);
                    if(currentAmount == this.core.getConfig("config.yml").getInt("checksBeforeAfk")) {
                        sendOutAfk(player);
                    } else {
                        checkAmounts.replace(player, currentAmount+1);
                    }
                } else {
                    checkAmounts.put(player, 1);
                }
            } else {
                sendOutNonAfk(player);
            }

            if(lastLocations.containsKey(player)) {
                lastLocations.replace(player, player.getLocation());
            } else {
                lastLocations.put(player, player.getLocation());
            }


        });
    }
}
