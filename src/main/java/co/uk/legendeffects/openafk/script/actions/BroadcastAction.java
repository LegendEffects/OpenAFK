package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.Action;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BroadcastAction extends Action {
    public BroadcastAction() {
        super("broadcast");
    }

    @Override
    public void execute(Player player, ActionType type, String[] args) {
        if(args.length == 0) {
            return;
        }

        if(args.length == 2 && args[1].matches("\\d+")) {
            String message = OpenAFK.parse(player, args[0]);
            for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                Bukkit.broadcastMessage(message);
            }
            return;
        }

        if(args.length > 2 && args[0].equals("PERM")) {
            for(String arg : Arrays.copyOfRange(args, 2, args.length)) {
                Bukkit.broadcast(OpenAFK.parse(player, arg), args[1]);
            }
            return;
        }

        for(String arg : args) {
            Bukkit.broadcastMessage(OpenAFK.parse(player, arg));
        }
    }
}
