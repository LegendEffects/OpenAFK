package co.uk.legendeffects.openafk.util;

import co.uk.legendeffects.openafk.OpenAFK;;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class CheckTask extends BukkitRunnable {
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
                plugin.makePlayerReturn(player, ActionType.RETURN, "onReturn");
                return;
            }

            Integer currentAmount = plugin.getCheckAmount(player);
            if(currentAmount == null) {
                plugin.setCheckAmount(player, 1);
                return;
            }

            if(currentAmount == this.plugin.getConfig().getInt("checksBeforeAfk")) {
                plugin.makePlayerAfk(player, ActionType.AFK, "onAfk");
                return;
            }
            plugin.setCheckAmount(player, currentAmount + 1);
        });
    }
}
