package co.uk.legendeffects.openafk.commands;

import co.uk.legendeffects.openafk.OpenAFK;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenAFKCommand implements CommandExecutor {

    private OpenAFK core;
    public OpenAFKCommand(OpenAFK core) {
        this.core = core;
    }

    public void showHelpMenu(Player player) {
        String genericCommand = ChatColor.RED + "/openafk ";

        player.sendMessage(ChatColor.GRAY + "---- " + ChatColor.BLUE + "OpenAFK Help" + ChatColor.GRAY + " ----");
        player.sendMessage(genericCommand + "help" + ChatColor.GRAY + " - Shows this help menu");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("openafk.admin")) {

            if(args.length == 0) {
                showHelpMenu((Player) sender);
            }



        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getMessages().getString("Prefix")+core.getMessages().getString("InsufficientPermissions")));
        }

        return true;
    }
}
