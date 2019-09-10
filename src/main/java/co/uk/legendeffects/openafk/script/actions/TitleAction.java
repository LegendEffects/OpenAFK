package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.Action;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.entity.Player;

public class TitleAction extends Action {
    public TitleAction() {
        super("title");
    }

    @Override
    public void execute(Player player, ActionType type, String[] args) {
        if(args.length != 1 && args.length != 5) {
            return;
        }

        if(args[0].equals("reset")) {
            player.resetTitle();
            return;
        }

        player.sendTitle(OpenAFK.parse(player, args[0]), OpenAFK.parse(player, args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[4]), Integer.parseInt(args[3]));
    }
}
