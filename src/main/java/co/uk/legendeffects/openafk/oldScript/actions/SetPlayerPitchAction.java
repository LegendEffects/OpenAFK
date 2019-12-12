package co.uk.legendeffects.openafk.oldScript.actions;

import co.uk.legendeffects.openafk.oldScript.Action;
import co.uk.legendeffects.openafk.oldScript.ActionType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetPlayerPitchAction extends Action {

    public SetPlayerPitchAction() {
        super("setpitch");
    }

    @Override
    public void execute(Player player, ActionType type, String[] args) {
        if(args.length != 1) {
            return;
        }

        if(!args[0].matches("[+-]?([0-9]*[.])?[0-9]+")) { // Check for float only.
            return;
        }

        Location newLocation = player.getLocation();
        newLocation.setPitch(Float.parseFloat(args[0]));

        player.teleport(newLocation);
    }
}
