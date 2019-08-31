package co.uk.legendeffects.openafk.commands;

import co.uk.legendeffects.openafk.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Test implements CommandExecutor {
    private Core core;
    public Test(Core core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 2) {
            sender.sendMessage("Invalid args count");
            return true;
        }

        String message = this.core.getConfig(args[0]).getString(args[1]);

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        return true;
    }
}
