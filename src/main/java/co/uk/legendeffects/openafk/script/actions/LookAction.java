package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class LookAction extends AbstractAction {
    public LookAction() {
        super("look");
    }

    @Override
    public void execute(Player player, ActionType type, Map<String, String> config) {
        Location location = player.getLocation();
        if(config.containsKey("pitch")) {
            location.setPitch(Float.parseFloat(config.get("pitch")));
        }

        if(config.containsKey("yaw")) {
            location.setYaw(Float.parseFloat(config.get("yaw")));
        }

        player.teleport(location);
    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(!actionConfig.containsKey("pitch") && !actionConfig.containsKey("yaw")) {
            plugin.getLogger().warning("[LookAction] Expected pitch or yaw parameters, found neither.");
            return false;
        }

        if(actionConfig.containsKey("pitch") && !actionConfig.get("pitch").matches("[+-]?([0-9]*[.])?[0-9]+")) {
            plugin.getLogger().warning("[LookAction] Pitch wasn't a valid float.");
            return false;
        }
        if(actionConfig.containsKey("yaw") && !actionConfig.get("yaw").matches("[+-]?([0-9]*[.])?[0-9]+")) {
            plugin.getLogger().warning("[LookAction] Yaw wasn't a valid float.");
            return false;
        }

        return true;
    }
}
