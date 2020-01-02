package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.script.ActionType;
import com.nametagedit.plugin.NametagEdit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class NametagAction extends AbstractAction {
    public NametagAction() {
        super("nametag");
    }

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {
        if(config.containsKey("prefix"))
            NametagEdit.getApi().setPrefix(player, config.get("prefix"));

        if(config.containsKey("suffix"))
            NametagEdit.getApi().setSuffix(player, config.get("suffix"));

    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(!Bukkit.getPluginManager().isPluginEnabled("NametagEdit")) {
            plugin.getLogger().warning("[NametagAction] The name tag action requires NametagEdit to be installed.");
            return false;
        }

        if(!actionConfig.containsKey("prefix") && !actionConfig.containsKey("suffix")) {
            plugin.getLogger().warning("[NametagAction] No prefix or suffix parameters were provided.");
            return false;
        }

        return true;
    }
}
