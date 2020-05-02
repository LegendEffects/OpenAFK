package co.uk.legendeffects.openafk.util;

import co.uk.legendeffects.openafk.OpenAFK;;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
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

        // Prevent an error where it cannot measure distance between 2 different worlds
        if(plugin.getLastLocation(player).getWorld() != player.getLocation().getWorld()) {
            plugin.setLastLocation(player, player.getLocation());
            return true;
        }

        boolean movedEnough = plugin.getLastLocation(player).distance(player.getLocation()) > this.plugin.getConfig().getInt("movementDistance");
        plugin.setLastLocation(player, player.getLocation());

        return movedEnough;
    }

    @Override
    public void run() {
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            // Debug
//            Integer checks = plugin.getCheckAmount(player);
//            if(checks != null) player.sendMessage("Checks: " + checks);

            // Cancel for conditions
            if(plugin.isExempt(player)) continue;
            if(player.hasPermission("openafk.exempt") && !player.isOp()) continue;

            FileConfiguration c = plugin.getConfig();
            if(c.getBoolean("detection.operatorsExempt") && player.isOp()) continue;

            if(!c.getBoolean("detection.gamemodes.survival") && player.getGameMode() == GameMode.SURVIVAL) continue;
            if(!c.getBoolean("detection.gamemodes.adventure") && player.getGameMode() == GameMode.ADVENTURE) continue;
            if(!c.getBoolean("detection.gamemodes.creative") && player.getGameMode() == GameMode.CREATIVE) continue;
            if(!c.getBoolean("detection.gamemodes.spectator") && player.getGameMode() == GameMode.SPECTATOR) continue;

            if(this.movedEnough(player)) {
                plugin.makePlayerReturn(player, ActionType.RETURN, "onReturn");
                continue;
            }

            Integer currentAmount = plugin.getCheckAmount(player);
            if(currentAmount == null) {
                plugin.setCheckAmount(player, 1);
                continue;
            }

            int checksBeforeAfk = this.plugin.getConfig().getInt("checksBeforeAfk");

            ConfigurationSection group = this.plugin.getGroups().getGroupForPlayer(player);
            if(group != null) {
                checksBeforeAfk = group.getInt("checkAmount", checksBeforeAfk);
            }

            if(currentAmount == checksBeforeAfk) {
                plugin.makePlayerAfk(player, ActionType.AFK, "onAfk");
                continue;
            }
            plugin.setCheckAmount(player, currentAmount + 1);
        }
    }
}
