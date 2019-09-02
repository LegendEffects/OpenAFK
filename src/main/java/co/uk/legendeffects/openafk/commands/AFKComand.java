package co.uk.legendeffects.openafk.commands;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKComand implements CommandExecutor {

    private final OpenAFK plugin;

    public AFKComand(OpenAFK plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(plugin.isAfkPlayer(player)) {
            PlayerReturnEvent event = new PlayerReturnEvent(player);
            Bukkit.getPluginManager().callEvent(event);

            if(!event.isCancelled()) {
                plugin.removeAfkPlayer(player);
                plugin.getActionRegistry().executeReturn(event);
            }
            return true;
        }

        PlayerAfkEvent event = new PlayerAfkEvent(player);
        Bukkit.getPluginManager().callEvent(event);

        if(!event.isCancelled()) {
            plugin.addAfkPlayer(player);

            plugin.getActionRegistry().executeAfk(event);
        }

        return true;
    }
}
