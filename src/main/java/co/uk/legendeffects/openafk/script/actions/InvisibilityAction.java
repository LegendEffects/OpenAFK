package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class InvisibilityAction extends AbstractAction {
    private final Plugin plugin;

    public InvisibilityAction(Plugin plugin) {
        super("invisibility");

        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {
        if(config.get("type").equalsIgnoreCase("hide")) {
            for(Player loopPlayer : Bukkit.getOnlinePlayers()) loopPlayer.hidePlayer(plugin, player);
        } else if(config.get("type").equalsIgnoreCase("show")) {
            for(Player loopPlayer : Bukkit.getOnlinePlayers()) loopPlayer.showPlayer(plugin, player);
        }
    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(!actionConfig.containsKey("type")) {
            plugin.getLogger().warning("[InvisibilityAction] Expected a type parameter (accepts \"show\" or \"hide\")");
            return false;
        }

        if(!actionConfig.get("type").equalsIgnoreCase("show") && !actionConfig.get("type").equalsIgnoreCase("hide")) {
            plugin.getLogger().warning("[InvisibilityAction] Expected type parameter to be \"show\" or \"hide\"");
            return false;
        }

        return true;
    }
}
