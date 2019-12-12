package co.uk.legendeffects.openafk.oldScript.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.oldScript.Action;
import co.uk.legendeffects.openafk.oldScript.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandAction extends Action {
    public CommandAction() {
        super("command");
    }

    @Override
    public void execute(Player player, ActionType type, String[] args) {
        if(args.length != 2) {
            if(args[0].equals("player")) {
                player.chat(OpenAFK.parse(player, args[1]));
                return;
            }
            if(args[0].equals("console")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), OpenAFK.parse(args[1]));
            }
        }
    }
}
