package co.uk.legendeffects.openafk.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.events.PlayerAfkEvent;
import co.uk.legendeffects.openafk.events.PlayerReturnEvent;
import co.uk.legendeffects.openafk.util.LocationHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class AfkAreaAction implements ActionExecutor {
    @Override
    public boolean isEnabled(OpenAFK plugin) {
        return plugin.getConfig().getBoolean("actions.afkArea.enabled");
    }

    @Override
    public void onAfk(PlayerAfkEvent event, OpenAFK plugin) {
        Player player = event.getPlayer();

        plugin.getPlayerData().getPlayer(player).set("location", LocationHelper.serialize(player.getLocation()));
        plugin.getPlayerData().savePlayer(player);

        Location afkLocation = LocationHelper.deserialize(plugin.getData().getRaw().getString("afkLocation"));
        player.teleport(afkLocation);
        plugin.setLastLocation(player, afkLocation);
    }

    @Override
    public void onReturn(PlayerReturnEvent event, OpenAFK plugin) {
        Player player = event.getPlayer();

        player.teleport(LocationHelper.deserialize(plugin.getPlayerData().getPlayer(player).getString("location")));
        plugin.getPlayerData().deletePlayer(player);
    }
}
