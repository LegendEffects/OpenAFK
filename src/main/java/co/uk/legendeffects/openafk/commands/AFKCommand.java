package co.uk.legendeffects.openafk.commands;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {

    private final OpenAFK plugin;

    public AFKCommand(OpenAFK plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("openafk.afk")) {
            sender.sendMessage(OpenAFK.parse(player, plugin.getConfig().getString("messages.insufficientPermissions")));
            return true;
        }

        if(plugin.isAfkPlayer(player)) {
            plugin.makePlayerReturn(player, ActionType.RETURN_BY_COMMAND, "onReturnCMD");
            return true;
        }

        plugin.makePlayerAfk(player, ActionType.AFK_BY_COMMAND, "onAfkCMD");
        return true;
    }
}
