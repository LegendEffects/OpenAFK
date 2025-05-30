package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.AbstractAction;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTabAction extends AbstractAction {
    protected final OpenAFK plugin;

    public AbstractTabAction(String id, OpenAFK plugin) {
        super(id);
        this.plugin = plugin;
    }

    protected TabPlayer getTabPlayer(@NotNull Player player) {
        TabPlayer tabPlayerByUuid = TabAPI.getInstance().getPlayer(player.getUniqueId());

        if (tabPlayerByUuid != null) {
            return tabPlayerByUuid;
        }

        TabPlayer tabPlayerByName = TabAPI.getInstance().getPlayer(player.getName());

        if (tabPlayerByName != null) {
            return tabPlayerByName;
        }

        plugin.getLogger().warning("Unable to get TabPlayer for " + player.getName() + " (" + player.getUniqueId() + ")");

        return null;
    }
}
