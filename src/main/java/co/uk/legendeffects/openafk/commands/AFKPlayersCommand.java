package co.uk.legendeffects.openafk.commands;

import co.uk.legendeffects.openafk.OpenAFK;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.StringJoiner;

public class AFKPlayersCommand implements CommandExecutor {

    private final OpenAFK plugin;

    public AFKPlayersCommand(OpenAFK plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //if(!(sender instanceof Player) || (args.length == 1 && args[0].equals("list"))) { Made only function until inventory is added
            StringJoiner list = new StringJoiner("&7, ");

            plugin.getAfkPlayers().forEach(player -> { list.add("&a"+player.getName()); });
            sender.sendMessage(OpenAFK.parse(plugin.getConfig().getString("messages.afkList.listPrefix") + list.toString()));
            return true;
        //}

        // TODO: Inventory of heads for AFK players when the sender is a player and didn't request in list form.

    }
}
