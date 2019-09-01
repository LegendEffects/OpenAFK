package co.uk.legendeffects.openafk.util;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class CheckTask extends BukkitRunnable {
    private HashMap<Player, Integer> checkAmounts = new HashMap<>();

    private final OpenAFK plugin;

    public CheckTask(OpenAFK plugin) {
        this.plugin = plugin;
    }

    private boolean movedEnough(Player player) {
        if(!plugin.lastLocations.containsKey(player)) return true;
        return plugin.lastLocations.get(player).distance(player.getLocation()) > this.plugin.getConfig().getInt("movementDistance");
    }

    private void sendOutAfk(Player player) {
        if(!plugin.afkPlayers.contains(player)) {
            PlayerAfkEvent event = new PlayerAfkEvent(false, player);
            plugin.getServer().getPluginManager().callEvent(event);

            plugin.afkPlayers.add(player);

            plugin.getActionOrchestrator().executeAfk(event);
        }
    }

    private void sendOutNonAfk(Player player) {
        if(plugin.afkPlayers.contains(player)) {
            PlayerReturnEvent event = new PlayerReturnEvent(false, player);
            plugin.getServer().getPluginManager().callEvent(event);

            plugin.afkPlayers.remove(player);
            checkAmounts.remove(player);

            plugin.getActionOrchestrator().executeReturn(event);
        }

    }

    @Override
    public void run() {
        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            if(!this.movedEnough(player)) {
                if(checkAmounts.containsKey(player)) {
                    int currentAmount = checkAmounts.get(player);
                    if(currentAmount == this.plugin.getConfig().getInt("checksBeforeAfk")) {
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

            if(plugin.lastLocations.containsKey(player)) {
                plugin.lastLocations.replace(player, player.getLocation());
            } else {
                plugin.lastLocations.put(player, player.getLocation());
            }
        });
    }
}
