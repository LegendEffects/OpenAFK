package co.uk.legendeffects.openafk.oldScript.actions;

import co.uk.legendeffects.openafk.oldScript.Action;
import co.uk.legendeffects.openafk.oldScript.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class InvisibilityAction extends Action {
    private final Plugin plugin;

    public InvisibilityAction(Plugin plugin) {
        super("invisibility");
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, ActionType type, String[] args) {
        if(args.length != 1) return;

        switch(args[0].toLowerCase()) {
            case "true":
                for(Player players : Bukkit.getOnlinePlayers()) players.hidePlayer(plugin, player);
                break;
            case "false":
                for(Player players : Bukkit.getOnlinePlayers()) players.showPlayer(plugin, player);
        }
    }
}
