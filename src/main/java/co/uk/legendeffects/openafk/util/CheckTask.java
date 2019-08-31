package co.uk.legendeffects.openafk.util;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class CheckTask extends BukkitRunnable {
    private HashMap<Player, Location> lastLocations = new HashMap<>();
    private HashMap<Player, Integer> checkAmounts = new HashMap<>();

    private final OpenAFK openAFK;

    public CheckTask(OpenAFK openAFK) {
        this.openAFK = openAFK;
    }

    private boolean movedEnough(Player player) {
        if(!lastLocations.containsKey(player)) return true;
        return lastLocations.get(player).distance(player.getLocation()) > this.openAFK.getConfig().getInt("movementDistance");
    }

    private void sendOutAfk(Player player) {
        if(!this.openAFK.afkPlayers.contains(player)) {
            this.openAFK.afkPlayers.add(player);

            PlayerAfkEvent event = new PlayerAfkEvent(false, player);
            this.openAFK.getServer().getPluginManager().callEvent(event);
        }
    }

    private void sendOutNonAfk(Player player) {
        if(this.openAFK.afkPlayers.contains(player)) {
            PlayerReturnEvent event = new PlayerReturnEvent(false, player);
            this.openAFK.getServer().getPluginManager().callEvent(event);

            this.openAFK.afkPlayers.remove(player);
            this.checkAmounts.remove(player);
        }

    }

    @Override
    public void run() {
        this.openAFK.getServer().getOnlinePlayers().forEach(player -> {
            if(!this.movedEnough(player)) {
                if(checkAmounts.containsKey(player)) {
                    int currentAmount = checkAmounts.get(player);
                    if(currentAmount == this.openAFK.getConfig().getInt("checksBeforeAfk")) {
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
