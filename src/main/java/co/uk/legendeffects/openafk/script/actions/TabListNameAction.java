package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.ActionType;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class TabListNameAction extends AbstractTabAction {
    public TabListNameAction(OpenAFK plugin) {
        super("tabListName", plugin);
    }

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {
        TabPlayer tabPlayer = this.getTabPlayer(player);

        if (tabPlayer == null) {
            return;
        }

        if (config.containsKey("prefix")) {
            TabAPI.getInstance().getTabListFormatManager().setPrefix(tabPlayer, config.get("prefix"));
        }

        if (config.containsKey("suffix")) {
            TabAPI.getInstance().getTabListFormatManager().setSuffix(tabPlayer, config.get("suffix"));
        }
    }

    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if (!Bukkit.getPluginManager().isPluginEnabled("TAB")) {
            plugin.getLogger().warning("[TabListNameAction] This action requires TAB to be installed.");
            return false;
        }

        if (TabAPI.getInstance().getTabListFormatManager() == null) {
            plugin.getLogger().warning("[TabListNameAction] The TabListFormatManager module of TAB needs to be enabled.");
            return false;
        }

        if (!actionConfig.containsKey("prefix") && !actionConfig.containsKey("suffix")) {
            plugin.getLogger().warning("[TabListNameAction] No prefix or suffix parameters were provided.");
            return false;
        }

        return true;
    }
}
