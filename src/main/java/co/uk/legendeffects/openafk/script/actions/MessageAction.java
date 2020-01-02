package co.uk.legendeffects.openafk.script.actions;

import co.uk.legendeffects.openafk.OpenAFK;
import co.uk.legendeffects.openafk.script.AbstractAction;
import co.uk.legendeffects.openafk.script.ActionType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class MessageAction extends AbstractAction {
    public MessageAction() {
        super("message");
    }

    @Override
    public void execute(Player player, ActionType type, Map<String,String> config) {
        if(config.containsKey("repeat")) {
            for(int i = 0; i < Integer.parseInt(config.get("repeat")); i++) {
                player.sendMessage(OpenAFK.parse(player, config.get("content")));
            }

            return;
        }

        player.sendMessage(OpenAFK.parse(player, config.get("content")));
    }

    @Override
    public boolean verifySyntax(Map<String, String> actionConfig, Plugin plugin) {
        if(!actionConfig.containsKey("content")) {
            plugin.getLogger().warning("[MessageAction] No message parameter was provided in the config.");
            return false;
        }

        return true;
    }

}
