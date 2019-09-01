package co.uk.legendeffects.openafk.util;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
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
        if(!plugin.lastLocations.containsKey(player)) return false;
        return plugin.lastLocations.get(player).distance(player.getLocation()) > this.plugin.getConfig().getInt("movementDistance");
    }

    private void sendOutAfk(Player player) {
        if(!plugin.afkPlayers.contains(player)) {
            PlayerAfkEvent event = new PlayerAfkEvent(false, player);
            plugin.getServer().getPluginManager().callEvent(event);

            if(!event.isCancelled()) {
                plugin.afkPlayers.add(player);

                plugin.getActionOrchestrator().executeAfk(event);
            }
        }
    }

    private void sendOutNonAfk(Player player) {
        if(plugin.afkPlayers.contains(player)) {
            PlayerReturnEvent event = new PlayerReturnEvent(false, player);
            plugin.getServer().getPluginManager().callEvent(event);

            if(!event.isCancelled()) {
                plugin.afkPlayers.remove(player);
                checkAmounts.remove(player);

                plugin.getActionOrchestrator().executeReturn(event);
            }
        }

    }

    @Override
    public void run() {
        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            // Cancel for conditions
            if(player.hasPermission("openafk.exempt")) return;

            FileConfiguration c = plugin.getConfig();
            if(c.getBoolean("detection.operatorsExempt") && player.isOp()) return;

            if(!c.getBoolean("detection.gamemodes.survival") && player.getGameMode() == GameMode.SURVIVAL) return;
            if(!c.getBoolean("detection.gamemodes.adventure") && player.getGameMode() == GameMode.ADVENTURE) return;
            if(!c.getBoolean("detection.gamemodes.creative") && player.getGameMode() == GameMode.CREATIVE) return;
            if(!c.getBoolean("detection.gamemodes.spectator") && player.getGameMode() == GameMode.SPECTATOR) return;

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
