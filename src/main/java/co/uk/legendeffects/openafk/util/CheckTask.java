package co.uk.legendeffects.openafk.util;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public final class CheckTask extends BukkitRunnable {
    private final HashMap<Player, Integer> checkAmounts = new HashMap<>();

    private final OpenAFK plugin;

    public CheckTask(OpenAFK plugin) {
        this.plugin = plugin;
    }

    private boolean movedEnough(Player player) {
        Location lastLocation = plugin.getLastLocation(player);
        if(lastLocation == null) {
            plugin.setLastLocation(player, player.getLocation());
            return false;
        }

        boolean movedEnough = plugin.getLastLocation(player).distance(player.getLocation()) > this.plugin.getConfig().getInt("movementDistance");
        plugin.setLastLocation(player, player.getLocation());

        return movedEnough;
    }

    private void sendOutAfk(Player player) {
        if(!plugin.isAfkPlayer(player)) {
            PlayerAfkEvent event = new PlayerAfkEvent(player);
            Bukkit.getPluginManager().callEvent(event);

            if(!event.isCancelled()) {
                plugin.addAfkPlayer(player);

                plugin.getActionRegistry().executeAfk(event);
            }
        }
    }

    private void sendOutNonAfk(Player player) {
        if(plugin.isAfkPlayer(player)) {
            PlayerReturnEvent event = new PlayerReturnEvent(player);
            Bukkit.getPluginManager().callEvent(event);

            if(!event.isCancelled()) {
                plugin.removeAfkPlayer(player);
                checkAmounts.remove(player);

                plugin.getActionRegistry().executeReturn(event);
            }
        }

    }

    @Override
    public void run() {
        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            // Cancel for conditions
            if(plugin.isExempt(player)) return;
            if(player.hasPermission("openafk.exempt") && !player.isOp()) return;

            FileConfiguration c = plugin.getConfig();
            if(c.getBoolean("detection.operatorsExempt") && player.isOp()) return;

            if(!c.getBoolean("detection.gamemodes.survival") && player.getGameMode() == GameMode.SURVIVAL) return;
            if(!c.getBoolean("detection.gamemodes.adventure") && player.getGameMode() == GameMode.ADVENTURE) return;
            if(!c.getBoolean("detection.gamemodes.creative") && player.getGameMode() == GameMode.CREATIVE) return;
            if(!c.getBoolean("detection.gamemodes.spectator") && player.getGameMode() == GameMode.SPECTATOR) return;

            if(this.movedEnough(player)) {
                sendOutNonAfk(player);
                return;
            }

            if(!checkAmounts.containsKey(player)) {
                checkAmounts.put(player, 1);
                return;
            }

            int currentAmount = checkAmounts.get(player);
            if(currentAmount == this.plugin.getConfig().getInt("checksBeforeAfk")) {
                sendOutAfk(player);
                return;
            }
            checkAmounts.put(player, currentAmount+1);
        });
    }
}
