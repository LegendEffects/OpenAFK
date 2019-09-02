package co.uk.legendeffects.openafk.commands;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.util.LocationHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenAFKCommand implements CommandExecutor {

    private final OpenAFK plugin;
    public OpenAFKCommand(OpenAFK core) {
        this.plugin = core;
    }

    public void showHelpMenu(Player player) {
        String genericCommand = ChatColor.RED + "/openafk ";

        player.sendMessage(ChatColor.GRAY + "---- " + ChatColor.BLUE + "OpenAFK Help" + ChatColor.GRAY + " ----");
        player.sendMessage(genericCommand + "set <afkArea>" + ChatColor.GRAY + " - Sets the AFK area");
        player.sendMessage(genericCommand + "help" + ChatColor.GRAY + " - Shows this help menu");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(!sender.hasPermission("openafk.admin")) {
            sender.sendMessage(OpenAFK.parse(player, plugin.getConfig().getString("messages.insufficientPermissions")));
            return true;
        }

        if(args.length == 0) {
            showHelpMenu(player);
            return true;
        }

        if(args[0].equalsIgnoreCase("set")) {
            if(args[1].equalsIgnoreCase("afkarea")) {
                plugin.getData().getRaw().set("afkLocation", LocationHelper.serialize(player.getLocation()));
                plugin.getData().save();
                player.sendMessage(OpenAFK.parse(player, plugin.getConfig().getString("messages.afkAreaSet")));
                return true;
            } else {
                player.sendMessage(OpenAFK.parse(player, plugin.getConfig().getString("messages.invalidUsage")));
            }
        } else if(args[0].equalsIgnoreCase("help")) {
            showHelpMenu(player);
        }

        return true;
    }
}
