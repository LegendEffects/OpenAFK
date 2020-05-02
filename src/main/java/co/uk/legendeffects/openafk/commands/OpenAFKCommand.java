package co.uk.legendeffects.openafk.commands;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.util.LocationHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.StringJoiner;

public class OpenAFKCommand implements CommandExecutor {
    private final OpenAFK plugin;

    //
    // Constructor
    //
    public OpenAFKCommand(OpenAFK plugin) {
        this.plugin = plugin;
    }

    //
    // Public
    //
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check permissions
        if(sender instanceof Player && !sender.hasPermission("openafk.admin")) {
            sender.sendMessage(OpenAFK.parse((Player) sender, plugin.getConfig().getString("messages.insufficientPermissions")));
            return true;
        }

        // Show help menu if there's 0 args.
        if(args.length == 0) {
            showHelpMenu(sender);
            return true;
        }

        // Parse the args
        switch(args[0].toLowerCase()) {
            case "set":
                set(sender, args);
                break;
            case "actionlist":
                sendAvailableActions(sender, args);
                break;
            case "reload":
                reloadPlugin(sender);
                break;
            case "help":
                showHelpMenu(sender);
                break;
            default:
                sender.sendMessage(OpenAFK.parse(plugin.getConfig().getString("messages.invalidUsage")));
                break;
        }

        return true;
    }

    //
    // Private
    //
    private void showHelpMenu(CommandSender sender) {
        String genericCommand = ChatColor.RED + "/openafk ";

        sender.sendMessage(ChatColor.GRAY + "---- " + ChatColor.BLUE + "OpenAFK Help" + ChatColor.GRAY + " ----");
        sender.sendMessage(genericCommand + "set <afkArea>" + ChatColor.GRAY + " - Sets the AFK area");
        sender.sendMessage(genericCommand + "actionlist" + ChatColor.GRAY + " - Shows the registered available actions");
        sender.sendMessage(genericCommand + "help" + ChatColor.GRAY + " - Shows this help menu");
        sender.sendMessage(genericCommand + "reload" + ChatColor.GRAY + " - Reloads configs");
    }

    private void sendAvailableActions(CommandSender sender, String[] args) {
        StringJoiner joiner = new StringJoiner(", ");

        for(String actionName : plugin.getActionParser().getActions().keySet()) {
            joiner.add(actionName);
        }

        sender.sendMessage("Available Actions: "+joiner.toString());
    }

    private void set(CommandSender sender, String[] args) {
        // Check if not a player
        if(!(sender instanceof Player)) {
            sender.sendMessage(OpenAFK.parse(plugin.getConfig().getString("messages.mustBeAPlayer")));
            return;
        }

        Player player = (Player) sender;

        if(args[1].equalsIgnoreCase("afkarea")) {
            plugin.getData().getRaw().set("afkLocation", LocationHelper.serialize(player.getLocation()));
            plugin.getData().save();

            player.sendMessage(OpenAFK.parse(player, plugin.getConfig().getString("messages.afkAreaSet")));
        } else {
            player.sendMessage(OpenAFK.parse(player, plugin.getConfig().getString("messages.invalidUsage")));
        }
    }

    private void reloadPlugin(CommandSender sender) {
        sender.sendMessage(OpenAFK.parse(plugin.getConfig().getString("messages.reloading")));
        plugin.reload();
        sender.sendMessage(OpenAFK.parse(plugin.getConfig().getString("messages.reloaded")));
    }
}
