package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.script.ActionType;
import co.uk.legendeffects.openafk.util.DataHandler;
import co.uk.legendeffects.openafk.util.LocationHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class AfkAreaAction extends AbstractAction {
    private final OpenAFK plugin;

    public AfkAreaAction(OpenAFK plugin) {
        super("afkarea");
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {
        DataHandler data = plugin.getPlayerData();
        if(plugin.isAfkPlayer(player)) {
            data.getPlayer(player).set("location", LocationHelper.serialize(player.getLocation()));
            data.savePlayer(player);

            Location afkLocation = LocationHelper.deserialize(plugin.getData().getRaw().getString("afkLocation"));
            player.teleport(afkLocation);
            plugin.setLastLocation(player, afkLocation);
            return;
        }

        player.teleport(LocationHelper.deserialize(plugin.getPlayerData().getPlayer(player).getString("location")));
        data.deletePlayer(player);
    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(!this.plugin.getData().getRaw().contains("afkLocation")) {
            plugin.getLogger().warning("[AfkAreaAction] No AFK area was set. Set it with /openafk set afkarea and then reload the server.");
            return false;
        }

        return true;
    }
}
