package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.Action;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.entity.Player;

public class MessageAction extends Action {
    public MessageAction() {
        super("message");
    }

    @Override
    public void execute(Player player, ActionType type, String[] args) {
        if(args.length == 0) {
            return;
        }

        if(args.length == 2 && args[1].matches("\\d+")) {
            String message = OpenAFK.parse(player, args[0]);

            for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                player.sendMessage(message);
            }

            return;
        }

        for (String arg : args) {
            player.sendMessage(OpenAFK.parse(player, arg));
        }
    }
}
