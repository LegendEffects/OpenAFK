package co.uk.legendeffects.openafk.commands;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.util.LocationHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenAFKCommand implements CommandExecutor {

    private OpenAFK plugin;
    public OpenAFKCommand(OpenAFK core) {
        this.plugin = core;
    }

    public void showHelpMenu(Player player) {
        String genericCommand = ChatColor.RED + "/openafk ";

        player.sendMessage(ChatColor.GRAY + "---- " + ChatColor.BLUE + "OpenAFK Help" + ChatColor.GRAY + " ----");
        player.sendMessage(genericCommand + "help" + ChatColor.GRAY + " - Shows this help menu");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("openafk.admin")) {
            Player player = (Player) sender;

            if(args.length == 0) {
                showHelpMenu(player);
                return true;
            }

            if(args[0].equalsIgnoreCase("setafkarea")) {
                plugin.getData().set("afkLocation", new LocationHelper().serialize(player.getLocation()));
                plugin.saveData();
                player.sendMessage("Set location.");
                return true;
            }



        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("Prefix")+plugin.getMessages().getString("InsufficientPermissions")));
        }

        return true;
    }
}
