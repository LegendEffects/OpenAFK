package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class BroadcastAction extends AbstractAction {
    public BroadcastAction() {
        super("broadcast");
    }

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {
        int loopTimes = Integer.parseInt(config.getOrDefault("repeat", "1"));

        for(int i = 0; i < loopTimes; i++) {
            if(config.containsKey("permission")) {
                Bukkit.broadcast(config.get("content"), config.get("permission"));
            } else {
                Bukkit.broadcastMessage(config.get("content"));
            }
        }
    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(!actionConfig.containsKey("content")) {
            plugin.getLogger().warning("[BroadcastAction] No content parameter was provided.");
            return false;
        }

        if(actionConfig.containsKey("repeat") && !actionConfig.get("repeat").matches("\\d+")) {
            plugin.getLogger().warning("[BroadcastAction] Repeat parameter wasn't a valid integer.");
            return false;
        }

        return true;
    }
}
