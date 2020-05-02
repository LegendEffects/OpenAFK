package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class CommandAction extends AbstractAction {
    public CommandAction() {
        super("command");
    }

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {
        if(config.getOrDefault("by", "player").equalsIgnoreCase("player")) {
            player.chat(OpenAFK.parse(player, config.get("command")));
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), OpenAFK.parse(player, config.get("command")));
        }
    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(actionConfig.containsKey("by") && (!actionConfig.get("by").equalsIgnoreCase("player") && !actionConfig.get("by").equalsIgnoreCase("console"))) {
            plugin.getLogger().warning("[CommandAction] Invalid by parameter. Expected 'player' or 'console'.");
            return false;
        }

        if(!actionConfig.containsKey("command")) {
            plugin.getLogger().warning("[CommandAction] No command parameter was found.");
            return false;
        }

        return true;
    }
}
