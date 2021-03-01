package co.uk.legendeffects.openafk.commands;

import co.uk.legendeffects.openafk.OpenAFK;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class IsAFKCommand implements CommandExecutor {
    private final OpenAFK plugin;

    public IsAFKCommand(OpenAFK plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("openafk.isafk")) {
            sender.sendMessage(OpenAFK.parse(plugin.getConfig().getString("messages.insufficientPermissions")));
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(OpenAFK.parse(plugin.getConfig().getString("messages.isAfk.notEnoughArgs")));
            return true;
        }

        if(args[0].length() == 36) {
            Player target = Bukkit.getPlayer(UUID.fromString(args[0]));
            return sendIsAFKResponse(sender, target);
        }

        Player target = Bukkit.getPlayer(args[0]);
        return sendIsAFKResponse(sender, target);
    }

    private boolean sendIsAFKResponse(CommandSender sender, Player target) {
        if(target == null) {
            sender.sendMessage(OpenAFK.parse(plugin.getConfig().getString("messages.isAfk.unknown")));
            return true;
        }

        if(plugin.isAfkPlayer(target)) {
            sender.sendMessage(OpenAFK.parse(target, plugin.getConfig().getString("messages.isAfk.afk")));
        } else {
            sender.sendMessage(OpenAFK.parse(target, plugin.getConfig().getString("messages.isAfk.notAfk")));
        }
        return true;
    }
}
